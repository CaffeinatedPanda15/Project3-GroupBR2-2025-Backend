package za.ac.cput.factories;

import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.domain.employees.Document;

import java.util.Date;

public class DocumentFactory {

    public static Document createDocument(String documentType, String fileName, String fileUrl,
                                         String mimeType, long fileSize, byte[] fileData,
                                         String uploadedBy, BackgroundCheck backgroundCheck) {
        if (documentType == null || documentType.trim().isEmpty()) {
            throw new IllegalArgumentException("Document type cannot be null or empty");
        }
        
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        
        if (mimeType == null || mimeType.trim().isEmpty()) {
            throw new IllegalArgumentException("MIME type cannot be null or empty");
        }
        
        if (fileData == null || fileData.length == 0) {
            throw new IllegalArgumentException("File data cannot be null or empty");
        }
        
        if (backgroundCheck == null) {
            throw new IllegalArgumentException("Background check cannot be null");
        }

        return new Document.Builder()
                .setDocumentType(documentType)
                .setFileName(fileName)
                .setFileUrl(fileUrl)
                .setMimeType(mimeType)
                .setFileSize(fileSize)
                .setFileData(fileData)
                .setUploadDate(new Date())
                .setUploadedBy(uploadedBy != null ? uploadedBy : "System")
                .setStatus("Pending")
                .setBackgroundCheck(backgroundCheck)
                .build();
    }

    public static Document createDocument(String documentType, String fileName,
                                         String mimeType, long fileSize, byte[] fileData,
                                         String uploadedBy, BackgroundCheck backgroundCheck) {
        return createDocument(documentType, fileName, null, mimeType, fileSize, 
                            fileData, uploadedBy, backgroundCheck);
    }
}
