package za.ac.cput.domain.parent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ChildSittingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;

    private Date sessionDate;
    private Time sessionStartTime;
    private Time sessionEndTime;
    private boolean sessionConfirmed;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status = SessionStatus.UPCOMING;

    @ManyToOne
    @JoinColumn(name = "nanny_id")
    private Nanny nanny;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChildSession> childSessions = new HashSet<>();

    protected ChildSittingSession() {}

    private ChildSittingSession(Builder builder) {
        this.sessionId = builder.sessionId;
        this.sessionDate = builder.sessionDate;
        this.sessionStartTime = builder.sessionStartTime;
        this.sessionEndTime = builder.sessionEndTime;
        this.sessionConfirmed = builder.sessionConfirmed;
        this.status = builder.status != null ? builder.status : SessionStatus.UPCOMING;
        this.nanny = builder.nanny;
        this.driver = builder.driver;
        this.payments = builder.payments;
        this.childSessions = builder.childSessions;
    }

    public int getSessionId() { return sessionId; }
    public Date getSessionDate() { return sessionDate; }
    public Time getSessionStartTime() { return sessionStartTime; }
    public Time getSessionEndTime() { return sessionEndTime; }
    public boolean isSessionConfirmed() { return sessionConfirmed; }
    public SessionStatus getStatus() { return status; }
    public void setStatus(SessionStatus status) { this.status = status; }
    public Nanny getNanny() { return nanny; }
    public Driver getDriver() { return driver; }
    public Set<Payment> getPayments() { return payments; }
    public Set<ChildSession> getChildSessions() { return childSessions; }

    public static class Builder {
        private int sessionId;
        private Date sessionDate;
        private Time sessionStartTime;
        private Time sessionEndTime;
        private boolean sessionConfirmed;
        private SessionStatus status = SessionStatus.UPCOMING;
        private Nanny nanny;
        private Driver driver;
        private Set<Payment> payments = new HashSet<>();
        private Set<ChildSession> childSessions = new HashSet<>();

        public Builder setSessionId(int sessionId) { this.sessionId = sessionId; return this; }
        public Builder setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; return this; }
        public Builder setSessionStartTime(Time sessionStartTime) { this.sessionStartTime = sessionStartTime; return this; }
        public Builder setSessionEndTime(Time sessionEndTime) { this.sessionEndTime = sessionEndTime; return this; }
        public Builder setSessionConfirmed(boolean sessionConfirmed) { this.sessionConfirmed = sessionConfirmed; return this; }
        public Builder setStatus(SessionStatus status) { this.status = status; return this; }
        public Builder setNanny(Nanny nanny) { this.nanny = nanny; return this; }
        public Builder setDriver(Driver driver) { this.driver = driver; return this; }
        public Builder setPayments(Set<Payment> payments) { this.payments = payments; return this; }
        public Builder setChildSessions(Set<ChildSession> childSessions) { this.childSessions = childSessions; return this; }

        public ChildSittingSession build() { return new ChildSittingSession(this); }

        public Builder copy(ChildSittingSession session) {
            this.sessionId = session.sessionId;
            this.sessionDate = session.sessionDate;
            this.sessionStartTime = session.sessionStartTime;
            this.sessionEndTime = session.sessionEndTime;
            this.sessionConfirmed = session.sessionConfirmed;
            this.status = session.status;
            this.nanny = session.nanny;
            this.driver = session.driver;
            this.payments = session.payments;
            this.childSessions = session.childSessions;
            return this;
        }
    }// End Builder class
}// End of class
