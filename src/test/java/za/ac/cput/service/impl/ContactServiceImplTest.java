package za.ac.cput.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Contact;
import za.ac.cput.factories.ContactFactory;
import za.ac.cput.service.IContactService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactServiceImplTest {

    @Autowired
    private IContactService contactService;

    private static Contact contact1;
    private static Contact contact2;

    @BeforeAll
    static void setUp() {
        contact1 = ContactFactory.createContact(123456789, 987654321, "john.doe@example.com");
        contact2 = ContactFactory.createContact(111222333, 444555666, "jane.smith@example.com");
    }

    @Test
    @Order(1)
    void create() {
        Contact created1 = contactService.create(contact1);
        assertNotNull(created1);
        assertEquals(contact1.getEmail(), created1.getEmail());
        System.out.println("Created Contact 1: " + created1);

        Contact created2 = contactService.create(contact2);
        assertNotNull(created2);
        assertEquals(contact2.getPhoneNumber1(), created2.getPhoneNumber1());
        System.out.println("Created Contact 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Contact read = contactService.read(1);
        assertNotNull(read);
        assertEquals("john.doe@example.com", read.getEmail());
        System.out.println("Read Contact: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Contact existing = contactService.read(1);
        assertNotNull(existing);

        Contact updated = new Contact.Builder()
                .setPhoneNumber1(existing.getPhoneNumber1())
                .setPhoneNumber2(existing.getPhoneNumber2())
                .setEmail("updated.email@example.com")
                .build();

        Contact result = contactService.update(updated);
        assertNotNull(result);
        assertEquals("updated.email@example.com", result.getEmail());
        System.out.println("Updated Contact: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Contact> contacts = contactService.getAll();
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());
        assertTrue(contacts.size() >= 2);
        System.out.println("All Contacts: " + contacts);
    }

    @Test
    @Order(5)
    void delete() {
        Contact toDelete = contactService.read(2);
        assertNotNull(toDelete);

        contactService.delete(2);
        Contact deleted = contactService.read(2);
        assertNull(deleted);
        System.out.println("Contact deleted successfully");
    }
}