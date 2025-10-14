package za.ac.cput.repositories.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.employees.Driver;

public interface IDriverRepository extends JpaRepository<Driver, Long> {
}
