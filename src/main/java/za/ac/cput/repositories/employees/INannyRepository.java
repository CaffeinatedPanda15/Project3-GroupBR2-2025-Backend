package za.ac.cput.repositories.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.employees.Nanny;

public interface INannyRepository extends JpaRepository<Nanny, Integer> {
}
