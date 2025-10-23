package za.ac.cput.repositories.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.admin.Admin;

import java.util.Optional;

public interface IAdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByUsernameAndPassword(String username, String password);

    Optional<Admin> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}