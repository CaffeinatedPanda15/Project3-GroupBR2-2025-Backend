package za.ac.cput.repositories.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.employees.BackgroundCheck;

public interface IBackgroundCheck extends JpaRepository<BackgroundCheck, Long> {
}
