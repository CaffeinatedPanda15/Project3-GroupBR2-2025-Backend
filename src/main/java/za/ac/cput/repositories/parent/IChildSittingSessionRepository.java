package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.parent.ChildSittingSession;

public interface IChildSittingSessionRepository extends JpaRepository<ChildSittingSession, Integer> {
}
