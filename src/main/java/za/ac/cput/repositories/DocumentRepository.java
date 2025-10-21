package za.ac.cput.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.employees.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByBackgroundCheckBackgroundCheckId(Integer backgroundCheckId);

    List<Document> findByDocumentType(String documentType);

    List<Document> findByStatus(String status);
}
