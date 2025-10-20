package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.factories.parent.ChildFactory;
import za.ac.cput.factories.parent.ChildSessionFactory;
import za.ac.cput.factories.parent.ChildSittingSessionFactory;
import za.ac.cput.factories.parent.ParentFactory;
import za.ac.cput.service.parent.IChildService;
import za.ac.cput.service.parent.IChildSessionService;
import za.ac.cput.service.parent.IChildSittingSessionService;
import za.ac.cput.service.parent.IParentService;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChildSessionServiceImplTest {

    @Autowired
    private IChildSessionService childSessionService;

    @Autowired
    private IChildService childService;

    @Autowired
    private IParentService parentService;

    @Autowired
    private IChildSittingSessionService sittingSessionService;

    private static ChildSession childSession1;
    private static ChildSession childSession2;
    private static Child child;
    private static ChildSittingSession sittingSession;

    @BeforeAll
    static void setUp() {
        // Will create in test method due to dependencies
    }

    @Test
    @Order(1)
    void create() {
        // Create parent
        Parent parent = ParentFactory.createParent("Emily", "Davis", new HashSet<>(), new HashSet<>());
        parent = parentService.create(parent);
        assertNotNull(parent);

        // Create child
        child = ChildFactory.createChild("Oliver", "Davis", 7, parent);
        child = childService.create(child);
        assertNotNull(child);

        // Create sitting session
        sittingSession = ChildSittingSessionFactory.createSession(
                new Date(), 
                Time.valueOf("10:00:00"), 
                Time.valueOf("16:00:00"), 
                true, 
                null, 
                null
        );
        sittingSession = sittingSessionService.create(sittingSession);
        assertNotNull(sittingSession);

        // Create child sessions
        childSession1 = ChildSessionFactory.createChildSession(child, sittingSession);
        ChildSession created1 = childSessionService.create(childSession1);
        assertNotNull(created1);
        assertEquals(child.getChildId(), created1.getChild().getChildId());
        System.out.println("Created Child Session 1: " + created1);

        childSession2 = ChildSessionFactory.createChildSession(child, sittingSession);
        ChildSession created2 = childSessionService.create(childSession2);
        assertNotNull(created2);
        System.out.println("Created Child Session 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        ChildSession read = childSessionService.read(1);
        assertNotNull(read);
        assertNotNull(read.getChild());
        System.out.println("Read Child Session: " + read);
    }

    @Test
    @Order(3)
    void update() {
        ChildSession existing = childSessionService.read(1);
        assertNotNull(existing);

        // For ChildSession, we can't update much since it's a linking table
        // But we can test the update method still works
        ChildSession updated = new ChildSession.Builder()
                .copy(existing)
                .build();

        ChildSession result = childSessionService.update(updated);
        assertNotNull(result);
        System.out.println("Updated Child Session: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<ChildSession> sessions = childSessionService.getAll();
        assertNotNull(sessions);
        assertFalse(sessions.isEmpty());
        assertTrue(sessions.size() >= 2);
        System.out.println("All Child Sessions: " + sessions);
    }

    @Test
    @Order(5)
    void getSessionsByChild() {
        List<ChildSession> sessions = childSessionService.getSessionsByChild(child.getChildId());
        assertNotNull(sessions);
        assertFalse(sessions.isEmpty());
        assertEquals(2, sessions.size());
        System.out.println("Sessions by Child: " + sessions);
    }

    @Test
    @Order(6)
    void delete() {
        ChildSession toDelete = childSessionService.read(2);
        assertNotNull(toDelete);

        childSessionService.delete(2);
        ChildSession deleted = childSessionService.read(2);
        assertNull(deleted);
        System.out.println("Child Session deleted successfully");
    }
}