package za.ac.cput.service.impl.parent;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.factories.parent.ParentFactory;
import za.ac.cput.service.parent.IParentService;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParentServiceImplTest {

    @Autowired
    private IParentService parentService;

    private static Parent parent1;
    private static Parent parent2;

    @BeforeAll
    static void setUp() {
        parent1 = ParentFactory.createParent("Michael", "Smith", new HashSet<>(), new HashSet<>());
        parent2 = ParentFactory.createParent("Sarah", "Johnson", new HashSet<>(), new HashSet<>());
    }

    @Test
    @Order(1)
    void create() {
        Parent created1 = parentService.create(parent1);
        assertNotNull(created1);
        assertEquals("Michael", created1.getParentName());
        assertEquals("Smith", created1.getParentSurname());
        System.out.println("Created Parent 1: " + created1);

        Parent created2 = parentService.create(parent2);
        assertNotNull(created2);
        assertEquals("Sarah", created2.getParentName());
        System.out.println("Created Parent 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Parent read = parentService.read(1);
        assertNotNull(read);
        assertEquals("Michael", read.getParentName());
        System.out.println("Read Parent: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Parent existing = parentService.read(1);
        assertNotNull(existing);

        Parent updated = new Parent.Builder()
                .copy(existing)
                .setParentName("Michael Updated")
                .build();

        Parent result = parentService.update(updated);
        assertNotNull(result);
        assertEquals("Michael Updated", result.getParentName());
        System.out.println("Updated Parent: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Parent> parents = parentService.getAll();
        assertNotNull(parents);
        assertFalse(parents.isEmpty());
        assertTrue(parents.size() >= 2);
        System.out.println("All Parents: " + parents);
    }

    @Test
    @Order(5)
    void delete() {
        Parent toDelete = parentService.read(2);
        assertNotNull(toDelete);

        parentService.delete(2);
        Parent deleted = parentService.read(2);
        assertNull(deleted);
        System.out.println("Parent deleted successfully");
    }
}