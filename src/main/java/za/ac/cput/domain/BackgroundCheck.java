package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class BackgroundCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int backgroundCheckId;

    private String status;
    private Date checkDate;
    private String verifiedBy;

    @OneToOne
    @JoinColumn(name = "nanny_id", unique = true)
    private Nanny nanny;

    protected BackgroundCheck() {}

    private BackgroundCheck(Builder builder) {
        this.backgroundCheckId = builder.backgroundCheckId;
        this.status = builder.status;
        this.checkDate = builder.checkDate;
        this.verifiedBy = builder.verifiedBy;
        this.nanny = builder.nanny;
    }

    public int getBackgroundCheckId() { return backgroundCheckId; }
    public String getStatus() { return status; }
    public Date getCheckDate() { return checkDate; }
    public String getVerifiedBy() { return verifiedBy; }
    public Nanny getNanny() { return nanny; }

    @Override
    public String toString() {
        return "BackgroundCheck{" +
                "backgroundCheckId=" + backgroundCheckId +
                ", status='" + status + '\'' +
                ", checkDate=" + checkDate +
                ", verifiedBy='" + verifiedBy + '\'' +
                ", nanny=" + nanny +
                '}';
    }

    public static class Builder {
        private int backgroundCheckId;
        private String status;
        private Date checkDate;
        private String verifiedBy;
        private Nanny nanny;

        public Builder setBackgroundCheckId(int backgroundCheckId) { this.backgroundCheckId = backgroundCheckId; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setCheckDate(Date checkDate) { this.checkDate = checkDate; return this; }
        public Builder setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; return this; }
        public Builder setNanny(Nanny nanny) { this.nanny = nanny; return this; }

        public BackgroundCheck build() { return new BackgroundCheck(this); }

        public Builder copy(BackgroundCheck backgroundCheck) {
            this.backgroundCheckId = backgroundCheck.backgroundCheckId;
            this.status = backgroundCheck.status;
            this.checkDate = backgroundCheck.checkDate;
            this.verifiedBy = backgroundCheck.verifiedBy;
            this.nanny = backgroundCheck.nanny;
            return this;
        }
    }// End of Builder class
}// End of BackgroundCheck class
