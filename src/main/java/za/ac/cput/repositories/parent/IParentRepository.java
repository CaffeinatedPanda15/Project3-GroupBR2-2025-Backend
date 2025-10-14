package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.parent.Parent;

public interface IParentRepository extends JpaRepository<Parent, Long> {
}
