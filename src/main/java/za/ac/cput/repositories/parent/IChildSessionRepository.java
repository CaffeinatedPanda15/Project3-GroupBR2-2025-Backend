package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.parent.ChildSession;

public interface IChildSessionRepository extends JpaRepository<ChildSession, Integer> {
}
