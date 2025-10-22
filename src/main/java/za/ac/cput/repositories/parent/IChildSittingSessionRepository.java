package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.SessionStatus;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.employees.Driver;

import java.util.List;

public interface IChildSittingSessionRepository extends JpaRepository<ChildSittingSession, Integer> {
    // Find all sessions assigned to a specific nanny
    List<ChildSittingSession> findByNanny(Nanny nanny);
    
    // Find all sessions assigned to a specific driver
    List<ChildSittingSession> findByDriver(Driver driver);
    
    // Find all sessions by nanny ID
    List<ChildSittingSession> findByNanny_NannyId(int nannyId);
    
    // Find all sessions by driver ID
    List<ChildSittingSession> findByDriver_DriverId(int driverId);
    
    // Find all sessions by nanny ID and status
    List<ChildSittingSession> findByNanny_NannyIdAndStatus(int nannyId, SessionStatus status);
    
    // Find all sessions by status
    List<ChildSittingSession> findByStatus(SessionStatus status);
}
