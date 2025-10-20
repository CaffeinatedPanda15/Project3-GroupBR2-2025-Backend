package za.ac.cput.service.impl.parent;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.factories.parent.ChildFactory;
import za.ac.cput.factories.parent.ParentFactory;
import za.ac.cput.service.parent.IChildService;
import za.ac.cput.service.parent.IParentService;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChildServiceImplTest {

    @Autowired
    private IChildService childService;

    @Autowired
    private IParentService parentService;

    private static Child child1;
    private static Child child2;
    private static Parent parent;

    @BeforeAll
    static void setUp() {
        // Will create parent in test method due to dependencies
    }

    @Test
    @Order(1)
    void create() {
        // Create parent first
        parent = ParentFactory.createParent("Robert", "Williams", new HashSet<>(), new HashSet<>());
        parent = parentService.create(parent);
        assertNotNull(parent);

        // Create children
        child1 = ChildFactory.createChild("Emma", "Williams", 5, parent);
        Child created1 = childService.create(child1);
        assertNotNull(created1);
        assertEquals("Emma", created1.getChildName());
        assertEquals(5, created1.getChildAge());
        System.out.println("Created Child 1: " + created1);

        child2 = ChildFactory.createChild("Liam", "Williams", 8, parent);
        Child created2 = childService.create(child2);
        assertNotNull(created2);
        assertEquals("Liam", created2.getChildName());
        System.out.println("Created Child 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Child read = childService.read(1);
        assertNotNull(read);
        assertEquals("Emma", read.getChildName());
        System.out.println("Read Child: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Child existing = childService.read(1);
        assertNotNull(existing);

        Child updated = new Child.Builder()
                .copy(existing)
                .setChildAge(6)
                .build();

        Child result = childService.update(updated);
        assertNotNull(result);
        assertEquals(6, result.getChildAge());
        System.out.println("Updated Child: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Child> children = childService.getAll();
        assertNotNull(children);
        assertFalse(children.isEmpty());
        assertTrue(children.size() >= 2);
        System.out.println("All Children: " + children);
    }

    @Test
    @Order(5)
    void getChildrenByParent() {
        List<Child> children = childService.getChildrenByParent(parent.getParentId());
        assertNotNull(children);
        assertFalse(children.isEmpty());
        assertEquals(2, children.size());
        System.out.println("Children by Parent: " + children);
    }

    @Test
    @Order(6)
    void delete() {
        Child toDelete = childService.read(2);
        assertNotNull(toDelete);

        childService.delete(2);
        Child deleted = childService.read(2);
        assertNull(deleted);
        System.out.println("Child deleted successfully");
    }
}