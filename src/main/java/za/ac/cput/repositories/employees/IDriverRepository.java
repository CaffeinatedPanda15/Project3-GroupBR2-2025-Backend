package za.ac.cput.repositories.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.employees.Driver;

import java.util.Optional;

public interface IDriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByEmail(String email);
}
