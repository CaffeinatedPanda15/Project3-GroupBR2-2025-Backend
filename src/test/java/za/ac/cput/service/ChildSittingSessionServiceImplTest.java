package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.factories.parent.ChildSittingSessionFactory;
import za.ac.cput.service.parent.IChildSittingSessionService;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChildSittingSessionServiceImplTest {

    @Autowired
    private IChildSittingSessionService sessionService;

    private static ChildSittingSession session1;
    private static ChildSittingSession session2;
    private static ChildSittingSession session3;

    @BeforeAll
    static void setUp() {
        session1 = ChildSittingSessionFactory.createSession(
                new Date(),
                Time.valueOf("08:00:00"),
                Time.valueOf("16:00:00"),
                true,
                null,
                null
        );

        session2 = ChildSittingSessionFactory.createSession(
                new Date(),
                Time.valueOf("09:00:00"),
                Time.valueOf("17:00:00"),
                false,
                null,
                null
        );

        session3 = ChildSittingSessionFactory.createSession(
                new Date(),
                Time.valueOf("10:00:00"),
                Time.valueOf("18:00:00"),
                true,
                null,
                null
        );
    }

    @Test
    @Order(1)
    void create() {
        ChildSittingSession created1 = sessionService.create(session1);
        assertNotNull(created1);
        assertTrue(created1.isSessionConfirmed());
        System.out.println("Created Session 1: " + created1);

        ChildSittingSession created2 = sessionService.create(session2);
        assertNotNull(created2);
        assertFalse(created2.isSessionConfirmed());
        System.out.println("Created Session 2: " + created2);

        ChildSittingSession created3 = sessionService.create(session3);
        assertNotNull(created3);
        assertTrue(created3.isSessionConfirmed());
        System.out.println("Created Session 3: " + created3);
    }

    @Test
    @Order(2)
    void read() {
        ChildSittingSession read = sessionService.read(1);
        assertNotNull(read);
        assertTrue(read.isSessionConfirmed());
        System.out.println("Read Session: " + read);
    }

    @Test
    @Order(3)
    void update() {
        ChildSittingSession existing = sessionService.read(2);
        assertNotNull(existing);
        assertFalse(existing.isSessionConfirmed());

        ChildSittingSession updated = new ChildSittingSession.Builder()
                .copy(existing)
                .setSessionConfirmed(true)
                .build();

        ChildSittingSession result = sessionService.update(updated);
        assertNotNull(result);
        assertTrue(result.isSessionConfirmed());
        System.out.println("Updated Session: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<ChildSittingSession> sessions = sessionService.getAll();
        assertNotNull(sessions);
        assertFalse(sessions.isEmpty());
        assertTrue(sessions.size() >= 3);
        System.out.println("All Sessions: " + sessions);
    }

    @Test
    @Order(5)
    void getConfirmedSessions() {
        List<ChildSittingSession> confirmedSessions = sessionService.getConfirmedSessions();
        assertNotNull(confirmedSessions);
        assertFalse(confirmedSessions.isEmpty());
        // After update, all sessions should be confirmed
        assertTrue(confirmedSessions.size() >= 3);
        confirmedSessions.forEach(s -> assertTrue(s.isSessionConfirmed()));
        System.out.println("Confirmed Sessions: " + confirmedSessions);
    }

    @Test
    @Order(6)
    void getPendingSessions() {
        List<ChildSittingSession> pendingSessions = sessionService.getPendingSessions();
        assertNotNull(pendingSessions);
        // After the update in test 3, all sessions are confirmed so this should be empty or have other pending sessions
        pendingSessions.forEach(s -> assertFalse(s.isSessionConfirmed()));
        System.out.println("Pending Sessions: " + pendingSessions);
    }

    @Test
    @Order(7)
    void delete() {
        ChildSittingSession toDelete = sessionService.read(3);
        assertNotNull(toDelete);

        sessionService.delete(3);
        ChildSittingSession deleted = sessionService.read(3);
        assertNull(deleted);
        System.out.println("Session deleted successfully");
    }
}