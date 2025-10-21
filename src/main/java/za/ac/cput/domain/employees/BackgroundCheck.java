package za.ac.cput.domain.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class BackgroundCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int backgroundCheckId;

    private String status;
    private Date checkDate;
    private String verifiedBy;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "nanny_id", unique = true)
    private Nanny nanny;

    @JsonIgnore
    @OneToMany(mappedBy = "backgroundCheck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    protected BackgroundCheck() {
    }

    private BackgroundCheck(Builder builder) {
        this.backgroundCheckId = builder.backgroundCheckId;
        this.status = builder.status;
        this.checkDate = builder.checkDate;
        this.verifiedBy = builder.verifiedBy;
        this.nanny = builder.nanny;
        this.documents = builder.documents;
    }

    public int getBackgroundCheckId() {
        return backgroundCheckId;
    }

    public String getStatus() {
        return status;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public Nanny getNanny() {
        return nanny;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return "BackgroundCheck{" +
                "backgroundCheckId=" + backgroundCheckId +
                ", status='" + status + '\'' +
                ", checkDate=" + checkDate +
                ", verifiedBy='" + verifiedBy + '\'' +
                ", nanny=" + nanny +
                ", documentsCount=" + (documents != null ? documents.size() : 0) +
                '}';
    }

    public static class Builder {
        private int backgroundCheckId;
        private String status;
        private Date checkDate;
        private String verifiedBy;
        private Nanny nanny;
        private List<Document> documents = new ArrayList<>();

        public Builder setBackgroundCheckId(int backgroundCheckId) {
            this.backgroundCheckId = backgroundCheckId;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setCheckDate(Date checkDate) {
            this.checkDate = checkDate;
            return this;
        }

        public Builder setVerifiedBy(String verifiedBy) {
            this.verifiedBy = verifiedBy;
            return this;
        }

        public Builder setNanny(Nanny nanny) {
            this.nanny = nanny;
            return this;
        }

        public Builder setDocuments(List<Document> documents) {
            this.documents = documents;
            return this;
        }

        public BackgroundCheck build() {
            return new BackgroundCheck(this);
        }

        public Builder copy(BackgroundCheck backgroundCheck) {
            this.backgroundCheckId = backgroundCheck.backgroundCheckId;
            this.status = backgroundCheck.status;
            this.checkDate = backgroundCheck.checkDate;
            this.verifiedBy = backgroundCheck.verifiedBy;
            this.nanny = backgroundCheck.nanny;
            this.documents = backgroundCheck.documents;
            return this;
        }
    }// End of Builder class
}// End of BackgroundCheck class
