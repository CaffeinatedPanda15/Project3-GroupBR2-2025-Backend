package za.ac.cput.repositories.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.employees.Nanny;

import java.util.Optional;

public interface INannyRepository extends JpaRepository<Nanny, Integer> {
    Optional<Nanny> findByEmail(String email);
}
