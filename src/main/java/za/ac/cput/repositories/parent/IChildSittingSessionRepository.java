package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Find all sessions by driver ID and status
    List<ChildSittingSession> findByDriver_DriverIdAndStatus(int driverId, SessionStatus status);

    // Find all sessions by status
    List<ChildSittingSession> findByStatus(SessionStatus status);

    // Find all sessions where any child belongs to the specified parent
    @Query("SELECT DISTINCT s FROM ChildSittingSession s " +
            "LEFT JOIN FETCH s.childSessions cs " +
            "LEFT JOIN FETCH cs.child c " +
            "LEFT JOIN FETCH c.parent p " +
            "LEFT JOIN FETCH s.nanny " +
            "LEFT JOIN FETCH s.driver " +
            "WHERE p.parentId = :parentId")
    List<ChildSittingSession> findByParentId(@Param("parentId") int parentId);
}
