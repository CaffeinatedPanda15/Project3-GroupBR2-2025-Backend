package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.parent.Child;

public interface IChildRepository extends JpaRepository<Child, Integer> {
}
