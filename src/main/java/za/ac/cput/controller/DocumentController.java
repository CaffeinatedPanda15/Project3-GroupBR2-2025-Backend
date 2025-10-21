package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.domain.employees.Document;
import za.ac.cput.service.IDocumentService;
import za.ac.cput.service.IBackgroundCheckService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS }, allowCredentials = "true")
public class DocumentController {

    private final IDocumentService documentService;
    private final IBackgroundCheckService backgroundCheckService;

    @Autowired
    public DocumentController(IDocumentService documentService,
            IBackgroundCheckService backgroundCheckService) {
        this.documentService = documentService;
        this.backgroundCheckService = backgroundCheckService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType,
            @RequestParam("backgroundCheckId") Integer backgroundCheckId,
            @RequestParam(value = "uploadedBy", required = false) String uploadedBy) {

        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== Document Upload Request ===");
            System.out.println("File name: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize() + " bytes");
            System.out.println("File type: " + file.getContentType());
            System.out.println("Document type: " + documentType);
            System.out.println("Background check ID: " + backgroundCheckId);

            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "File is empty");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            BackgroundCheck backgroundCheck = backgroundCheckService.read(backgroundCheckId);
            if (backgroundCheck == null) {
                // Auto-create a background check for testing purposes
                backgroundCheck = new BackgroundCheck.Builder()
                        .setStatus("Pending")
                        .setCheckDate(new Date())
                        .setVerifiedBy("System")
                        .build();
                backgroundCheck = backgroundCheckService.create(backgroundCheck);
                System.out.println("Created new background check with ID: " + backgroundCheck.getBackgroundCheckId());
            } else {
                System.out
                        .println("Using existing background check with ID: " + backgroundCheck.getBackgroundCheckId());
            }

            // Create document entity
            Document document = new Document.Builder()
                    .setDocumentType(documentType)
                    .setFileName(file.getOriginalFilename())
                    .setMimeType(file.getContentType())
                    .setFileSize(file.getSize())
                    .setFileData(file.getBytes())
                    .setUploadDate(new Date())
                    .setUploadedBy(uploadedBy != null ? uploadedBy : "User")
                    .setStatus("Pending")
                    .setBackgroundCheck(backgroundCheck)
                    .build();

            System.out.println("Saving document to database...");
            Document savedDocument = documentService.create(document);
            System.out.println("Document saved successfully with ID: " + savedDocument.getDocumentId());

            response.put("success", true);
            response.put("message", "Document uploaded successfully");
            response.put("documentId", savedDocument.getDocumentId());
            response.put("fileName", savedDocument.getFileName());
            response.put("documentType", savedDocument.getDocumentType());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("ERROR uploading document: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error uploading document: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<Map<String, Object>> uploadMultipleDocuments(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("backgroundCheckId") Integer backgroundCheckId,
            @RequestParam(value = "uploadedBy", required = false) String uploadedBy) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (files.size() != documentTypes.size()) {
                response.put("success", false);
                response.put("message", "Number of files and document types must match");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            BackgroundCheck backgroundCheck = backgroundCheckService.read(backgroundCheckId);
            if (backgroundCheck == null) {
                // Auto-create a background check for testing purposes
                backgroundCheck = new BackgroundCheck.Builder()
                        .setStatus("Pending")
                        .setCheckDate(new Date())
                        .setVerifiedBy("System")
                        .build();
                backgroundCheck = backgroundCheckService.create(backgroundCheck);
                System.out.println("Created new background check with ID: " + backgroundCheck.getBackgroundCheckId());
            }

            List<Map<String, Object>> uploadedDocuments = new java.util.ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String documentType = documentTypes.get(i);

                if (!file.isEmpty()) {
                    Document document = new Document.Builder()
                            .setDocumentType(documentType)
                            .setFileName(file.getOriginalFilename())
                            .setMimeType(file.getContentType())
                            .setFileSize(file.getSize())
                            .setFileData(file.getBytes())
                            .setUploadDate(new Date())
                            .setUploadedBy(uploadedBy != null ? uploadedBy : "User")
                            .setStatus("Pending")
                            .setBackgroundCheck(backgroundCheck)
                            .build();

                    Document savedDocument = documentService.create(document);

                    Map<String, Object> docInfo = new HashMap<>();
                    docInfo.put("documentId", savedDocument.getDocumentId());
                    docInfo.put("fileName", savedDocument.getFileName());
                    docInfo.put("documentType", savedDocument.getDocumentType());
                    uploadedDocuments.add(docInfo);
                }
            }

            response.put("success", true);
            response.put("message", uploadedDocuments.size() + " documents uploaded successfully");
            response.put("documents", uploadedDocuments);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error uploading documents: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Integer id) {
        try {
            Document document = documentService.read(id);

            if (document == null || document.getFileData() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(document.getMimeType()));
            headers.setContentDispositionFormData("attachment", document.getFileName());
            headers.setContentLength(document.getFileSize());

            return new ResponseEntity<>(document.getFileData(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/background-check/{backgroundCheckId}")
    public ResponseEntity<List<Document>> getDocumentsByBackgroundCheck(@PathVariable Integer backgroundCheckId) {
        try {
            List<Document> documents = documentService.getDocumentsByBackgroundCheckId(backgroundCheckId);
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Integer id) {
        Document document = documentService.read(id);
        if (document != null) {
            return new ResponseEntity<>(document, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAll();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Map<String, Object>> updateDocumentStatus(
            @PathVariable Integer id,
            @RequestParam String status) {

        Map<String, Object> response = new HashMap<>();

        try {
            Document document = documentService.read(id);
            if (document == null) {
                response.put("success", false);
                response.put("message", "Document not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Document updatedDocument = new Document.Builder()
                    .copy(document)
                    .setStatus(status)
                    .build();

            documentService.update(updatedDocument);

            response.put("success", true);
            response.put("message", "Document status updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating document status: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteDocument(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Document document = documentService.read(id);
            if (document == null) {
                response.put("success", false);
                response.put("message", "Document not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            documentService.delete(id);

            response.put("success", true);
            response.put("message", "Document deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting document: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-or-create-background-check")
    public ResponseEntity<Map<String, Object>> getOrCreateBackgroundCheck(
            @RequestParam(value = "userId", required = false) String userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // For now, just create a new background check
            BackgroundCheck backgroundCheck = new BackgroundCheck.Builder()
                    .setStatus("Pending")
                    .setCheckDate(new Date())
                    .setVerifiedBy("System")
                    .build();

            backgroundCheck = backgroundCheckService.create(backgroundCheck);

            response.put("success", true);
            response.put("backgroundCheckId", backgroundCheck.getBackgroundCheckId());
            response.put("status", backgroundCheck.getStatus());
            response.put("message", "Background check created successfully");

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating background check: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
