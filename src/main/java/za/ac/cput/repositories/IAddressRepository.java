package za.ac.cput.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;

public interface IAddressRepository extends JpaRepository<Address, Integer> {
    interface IContactRepository extends JpaRepository<Contact, Long> {
    }
}
