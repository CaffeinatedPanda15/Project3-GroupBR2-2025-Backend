package za.ac.cput.service;

import za.ac.cput.domain.employees.Document;

import java.util.List;

public interface IDocumentService extends IService<Document, Integer> {
    void delete(Integer id);

    List<Document> getDocumentsByBackgroundCheckId(Integer backgroundCheckId);

    List<Document> getDocumentsByType(String documentType);

    List<Document> getDocumentsByStatus(String status);
}
