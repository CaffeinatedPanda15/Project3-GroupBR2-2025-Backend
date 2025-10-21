package za.ac.cput.domain.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.ac.cput.domain.parent.ChildSittingSession;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    private String driverName;
    private String driverSurname;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChildSittingSession> sessions = new HashSet<>();

    protected Driver() {}

    private Driver(Builder builder) {
        this.driverId = builder.driverId;
        this.driverName = builder.driverName;
        this.driverSurname = builder.driverSurname;
        this.email = builder.email;
        this.password = builder.password;
        this.sessions = builder.sessions;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverSurname='" + driverSurname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sessions=" + sessions +
                '}';
    }

    public int getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public String getDriverSurname() { return driverSurname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Set<ChildSittingSession> getSessions() { return sessions; }

    public static class Builder {
        private int driverId;
        private String driverName;
        private String driverSurname;
        private String email;
        private String password;
        private Set<ChildSittingSession> sessions = new HashSet<>();

        public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
        public Builder setDriverName(String driverName) { this.driverName = driverName; return this; }
        public Builder setDriverSurname(String driverSurname) { this.driverSurname = driverSurname; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setSessions(Set<ChildSittingSession> sessions) { this.sessions = sessions; return this; }

        public Driver build() { return new Driver(this); }

        public Builder copy(Driver driver) {
            this.driverId = driver.driverId;
            this.driverName = driver.driverName;
            this.driverSurname = driver.driverSurname;
            this.email = driver.email;
            this.password = driver.password;
            this.sessions = driver.sessions;
            return this;
        }
    }// End of Builder class
}// End of class
