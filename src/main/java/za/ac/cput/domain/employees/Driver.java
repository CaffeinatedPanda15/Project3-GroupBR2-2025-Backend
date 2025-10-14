package za.ac.cput.domain.employees;

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

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChildSittingSession> sessions = new HashSet<>();

    protected Driver() {}

    private Driver(Builder builder) {
        this.driverId = builder.driverId;
        this.driverName = builder.driverName;
        this.driverSurname = builder.driverSurname;
        this.sessions = builder.sessions;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverSurname='" + driverSurname + '\'' +
                ", sessions=" + sessions +
                '}';
    }

    public int getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public String getDriverSurname() { return driverSurname; }
    public Set<ChildSittingSession> getSessions() { return sessions; }

    public static class Builder {
        private int driverId;
        private String driverName;
        private String driverSurname;
        private Set<ChildSittingSession> sessions = new HashSet<>();

        public Builder setDriverId(int driverId) { this.driverId = driverId; return this; }
        public Builder setDriverName(String driverName) { this.driverName = driverName; return this; }
        public Builder setDriverSurname(String driverSurname) { this.driverSurname = driverSurname; return this; }
        public Builder setSessions(Set<ChildSittingSession> sessions) { this.sessions = sessions; return this; }

        public Driver build() { return new Driver(this); }

        public Builder copy(Driver driver) {
            this.driverId = driver.driverId;
            this.driverName = driver.driverName;
            this.driverSurname = driver.driverSurname;
            this.sessions = driver.sessions;
            return this;
        }
    }// End of Builder class
}// End of class
