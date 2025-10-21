package za.ac.cput.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.employees.Document;
import za.ac.cput.repositories.DocumentRepository;
import za.ac.cput.service.IDocumentService;

import java.util.List;

@Service
public class DocumentServiceImpl implements IDocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Document create(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document read(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document update(Document document) {
        if (documentRepository.existsById(document.getDocumentId())) {
            return documentRepository.save(document);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        documentRepository.deleteById(id);
    }

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> getDocumentsByBackgroundCheckId(Integer backgroundCheckId) {
        return documentRepository.findByBackgroundCheckBackgroundCheckId(backgroundCheckId);
    }

    @Override
    public List<Document> getDocumentsByType(String documentType) {
        return documentRepository.findByDocumentType(documentType);
    }

    @Override
    public List<Document> getDocumentsByStatus(String status) {
        return documentRepository.findByStatus(status);
    }
}
