package za.ac.cput.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Contact;

public interface IContactRepository extends JpaRepository<Contact, Long> {
}
