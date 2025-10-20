package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.factories.employees.BackgroundCheckFactory;
import za.ac.cput.factories.employees.NannyFactory;
import za.ac.cput.service.employees.IBackgroundCheckService;
import za.ac.cput.service.employees.INannyService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BackgroundCheckServiceImplTest {

    @Autowired
    private IBackgroundCheckService backgroundCheckService;

    @Autowired
    private INannyService nannyService;

    private static BackgroundCheck backgroundCheck1;
    private static BackgroundCheck backgroundCheck2;
    private static Nanny nanny1;
    private static Nanny nanny2;

    @BeforeAll
    static void setUp() {
        // Will create in test method due to dependencies
    }

    @Test
    @Order(1)
    void create() {
        // Create nannies first
        nanny1 = NannyFactory.createNanny("Alice", "Johnson", new HashSet<>(), new HashSet<>());
        nanny1 = nannyService.create(nanny1);
        assertNotNull(nanny1);

        nanny2 = NannyFactory.createNanny("Betty", "Williams", new HashSet<>(), new HashSet<>());
        nanny2 = nannyService.create(nanny2);
        assertNotNull(nanny2);

        // Create background checks
        backgroundCheck1 = BackgroundCheckFactory.createBackgroundCheck(
                "Approved", 
                new Date(), 
                "Security Agency A", 
                nanny1
        );
        BackgroundCheck created1 = backgroundCheckService.create(backgroundCheck1);
        assertNotNull(created1);
        assertEquals("Approved", created1.getStatus());
        System.out.println("Created Background Check 1 - ID: " + created1.getBackgroundCheckId() + ", Status: " + created1.getStatus());

        backgroundCheck2 = BackgroundCheckFactory.createBackgroundCheck(
                "Pending", 
                new Date(), 
                "Security Agency B", 
                nanny2
        );
        BackgroundCheck created2 = backgroundCheckService.create(backgroundCheck2);
        assertNotNull(created2);
        assertEquals("Pending", created2.getStatus());
        System.out.println("Created Background Check 2 - ID: " + created2.getBackgroundCheckId() + ", Status: " + created2.getStatus());
    }

    @Test
    @Order(2)
    void read() {
        BackgroundCheck read = backgroundCheckService.read(1);
        assertNotNull(read);
        assertEquals("Approved", read.getStatus());
        System.out.println("Read Background Check ID: " + read.getBackgroundCheckId() + 
                           ", Status: " + read.getStatus() + 
                           ", Verified By: " + read.getVerifiedBy());
    }

    @Test
    @Order(3)
    void update() {
        BackgroundCheck existing = backgroundCheckService.read(1);
        assertNotNull(existing);

        BackgroundCheck updated = new BackgroundCheck.Builder()
                .copy(existing)
                .setStatus("Verified")
                .build();

        BackgroundCheck result = backgroundCheckService.update(updated);
        assertNotNull(result);
        assertEquals("Verified", result.getStatus());
        System.out.println("Updated Background Check - ID: " + result.getBackgroundCheckId() + ", Status: " + result.getStatus());
    }

    @Test
    @Order(4)
    void getAll() {
        List<BackgroundCheck> checks = backgroundCheckService.getAll();
        assertNotNull(checks);
        assertFalse(checks.isEmpty());
        assertTrue(checks.size() >= 2);
        System.out.println("Found " + checks.size() + " Background Checks");
    }

    @Test
    @Order(5)
    void getByNannyId() {
        BackgroundCheck check = backgroundCheckService.getByNannyId(nanny1.getNannyId());
        assertNotNull(check);
        assertEquals("Verified", check.getStatus());
        System.out.println("Background Check by Nanny ID - ID: " + check.getBackgroundCheckId() + ", Status: " + check.getStatus());
    }

    @Test
    @Order(6)
    void delete() {
        BackgroundCheck toDelete = backgroundCheckService.read(2);
        assertNotNull(toDelete);

        backgroundCheckService.delete(2);
        BackgroundCheck deleted = backgroundCheckService.read(2);
        assertNull(deleted);
        System.out.println("Background Check deleted successfully");
    }
}