package za.ac.cput.factories;

import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.domain.employees.Document;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class DocumentFactoryTest {

    @Test
    void createDocument_Success() {
        BackgroundCheck backgroundCheck = new BackgroundCheck.Builder()
                .setBackgroundCheckId(1)
                .setStatus("Pending")
                .setCheckDate(new Date())
                .build();

        byte[] fileData = "Test file content".getBytes();

        Document document = DocumentFactory.createDocument(
                "Reference",
                "reference.pdf",
                "application/pdf",
                fileData.length,
                fileData,
                "testUser",
                backgroundCheck
        );

        assertNotNull(document);
        assertEquals("Reference", document.getDocumentType());
        assertEquals("reference.pdf", document.getFileName());
        assertEquals("application/pdf", document.getMimeType());
        assertEquals(fileData.length, document.getFileSize());
        assertArrayEquals(fileData, document.getFileData());
        assertEquals("testUser", document.getUploadedBy());
        assertEquals("Pending", document.getStatus());
        assertNotNull(document.getUploadDate());
        assertEquals(backgroundCheck, document.getBackgroundCheck());
    }

    @Test
    void createDocument_NullDocumentType_ThrowsException() {
        BackgroundCheck backgroundCheck = new BackgroundCheck.Builder()
                .setBackgroundCheckId(1)
                .build();

        byte[] fileData = "Test file content".getBytes();

        assertThrows(IllegalArgumentException.class, () -> {
            DocumentFactory.createDocument(
                    null,
                    "test.pdf",
                    "application/pdf",
                    fileData.length,
                    fileData,
                    "testUser",
                    backgroundCheck
            );
        });
    }

    @Test
    void createDocument_EmptyFileName_ThrowsException() {
        BackgroundCheck backgroundCheck = new BackgroundCheck.Builder()
                .setBackgroundCheckId(1)
                .build();

        byte[] fileData = "Test file content".getBytes();

        assertThrows(IllegalArgumentException.class, () -> {
            DocumentFactory.createDocument(
                    "Reference",
                    "",
                    "application/pdf",
                    fileData.length,
                    fileData,
                    "testUser",
                    backgroundCheck
            );
        });
    }

    @Test
    void createDocument_NullFileData_ThrowsException() {
        BackgroundCheck backgroundCheck = new BackgroundCheck.Builder()
                .setBackgroundCheckId(1)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            DocumentFactory.createDocument(
                    "Reference",
                    "test.pdf",
                    "application/pdf",
                    0,
                    null,
                    "testUser",
                    backgroundCheck
            );
        });
    }

    @Test
    void createDocument_NullBackgroundCheck_ThrowsException() {
        byte[] fileData = "Test file content".getBytes();

        assertThrows(IllegalArgumentException.class, () -> {
            DocumentFactory.createDocument(
                    "Reference",
                    "test.pdf",
                    "application/pdf",
                    fileData.length,
                    fileData,
                    "testUser",
                    null
            );
        });
    }
}
