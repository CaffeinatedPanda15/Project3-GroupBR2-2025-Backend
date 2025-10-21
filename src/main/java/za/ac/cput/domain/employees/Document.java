package za.ac.cput.domain.employees;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    private String documentType; // Reference, Police Clearance, Driver Test, Drivers License
    private String fileName;
    private String fileUrl; // Path where the file is stored
    private String mimeType; // File type (pdf, image, etc.)
    private long fileSize; // File size in bytes
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData; // Store the actual file data
    
    private Date uploadDate;
    private String uploadedBy; // User who uploaded
    private String status; // Pending, Approved, Rejected

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "background_check_id")
    private BackgroundCheck backgroundCheck;

    protected Document() {}

    private Document(Builder builder) {
        this.documentId = builder.documentId;
        this.documentType = builder.documentType;
        this.fileName = builder.fileName;
        this.fileUrl = builder.fileUrl;
        this.mimeType = builder.mimeType;
        this.fileSize = builder.fileSize;
        this.fileData = builder.fileData;
        this.uploadDate = builder.uploadDate;
        this.uploadedBy = builder.uploadedBy;
        this.status = builder.status;
        this.backgroundCheck = builder.backgroundCheck;
    }

    public int getDocumentId() { return documentId; }
    public String getDocumentType() { return documentType; }
    public String getFileName() { return fileName; }
    public String getFileUrl() { return fileUrl; }
    public String getMimeType() { return mimeType; }
    public long getFileSize() { return fileSize; }
    public byte[] getFileData() { return fileData; }
    public Date getUploadDate() { return uploadDate; }
    public String getUploadedBy() { return uploadedBy; }
    public String getStatus() { return status; }
    public BackgroundCheck getBackgroundCheck() { return backgroundCheck; }

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", documentType='" + documentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", fileSize=" + fileSize +
                ", uploadDate=" + uploadDate +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {
        private int documentId;
        private String documentType;
        private String fileName;
        private String fileUrl;
        private String mimeType;
        private long fileSize;
        private byte[] fileData;
        private Date uploadDate;
        private String uploadedBy;
        private String status;
        private BackgroundCheck backgroundCheck;

        public Builder setDocumentId(int documentId) { 
            this.documentId = documentId; 
            return this; 
        }
        
        public Builder setDocumentType(String documentType) { 
            this.documentType = documentType; 
            return this; 
        }
        
        public Builder setFileName(String fileName) { 
            this.fileName = fileName; 
            return this; 
        }
        
        public Builder setFileUrl(String fileUrl) { 
            this.fileUrl = fileUrl; 
            return this; 
        }
        
        public Builder setMimeType(String mimeType) { 
            this.mimeType = mimeType; 
            return this; 
        }
        
        public Builder setFileSize(long fileSize) { 
            this.fileSize = fileSize; 
            return this; 
        }
        
        public Builder setFileData(byte[] fileData) { 
            this.fileData = fileData; 
            return this; 
        }
        
        public Builder setUploadDate(Date uploadDate) { 
            this.uploadDate = uploadDate; 
            return this; 
        }
        
        public Builder setUploadedBy(String uploadedBy) { 
            this.uploadedBy = uploadedBy; 
            return this; 
        }
        
        public Builder setStatus(String status) { 
            this.status = status; 
            return this; 
        }
        
        public Builder setBackgroundCheck(BackgroundCheck backgroundCheck) { 
            this.backgroundCheck = backgroundCheck; 
            return this; 
        }

        public Document build() { 
            return new Document(this); 
        }

        public Builder copy(Document document) {
            this.documentId = document.documentId;
            this.documentType = document.documentType;
            this.fileName = document.fileName;
            this.fileUrl = document.fileUrl;
            this.mimeType = document.mimeType;
            this.fileSize = document.fileSize;
            this.fileData = document.fileData;
            this.uploadDate = document.uploadDate;
            this.uploadedBy = document.uploadedBy;
            this.status = document.status;
            this.backgroundCheck = document.backgroundCheck;
            return this;
        }
    }
}
