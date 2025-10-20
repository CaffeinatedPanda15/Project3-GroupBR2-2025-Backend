package za.ac.cput.service.impl.employees;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.factories.employees.NannyFactory;
import za.ac.cput.service.employees.INannyService;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NannyServiceImplTest {

    @Autowired
    private INannyService nannyService;

    private static Nanny nanny1;
    private static Nanny nanny2;

    @BeforeAll
    static void setUp() {
        nanny1 = NannyFactory.createNanny("Mary", "Poppins", new HashSet<>(), new HashSet<>());
        nanny2 = NannyFactory.createNanny("Jane", "Banks", new HashSet<>(), new HashSet<>());
    }

    @Test
    @Order(1)
    void create() {
        Nanny created1 = nannyService.create(nanny1);
        assertNotNull(created1);
        assertEquals("Mary", created1.getNannyName());
        assertEquals("Poppins", created1.getNannySurname());
        System.out.println("Created Nanny 1: " + created1);

        Nanny created2 = nannyService.create(nanny2);
        assertNotNull(created2);
        assertEquals("Jane", created2.getNannyName());
        System.out.println("Created Nanny 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Nanny read = nannyService.read(1);
        assertNotNull(read);
        assertEquals("Mary", read.getNannyName());
        System.out.println("Read Nanny: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Nanny existing = nannyService.read(1);
        assertNotNull(existing);

        Nanny updated = new Nanny.Builder()
                .copy(existing)
                .setNannyName("Mary Updated")
                .build();

        Nanny result = nannyService.update(updated);
        assertNotNull(result);
        assertEquals("Mary Updated", result.getNannyName());
        System.out.println("Updated Nanny: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Nanny> nannies = nannyService.getAll();
        assertNotNull(nannies);
        assertFalse(nannies.isEmpty());
        assertTrue(nannies.size() >= 2);
        System.out.println("All Nannies: " + nannies);
    }

    @Test
    @Order(5)
    void delete() {
        Nanny toDelete = nannyService.read(2);
        assertNotNull(toDelete);

        nannyService.delete(2);
        Nanny deleted = nannyService.read(2);
        assertNull(deleted);
        System.out.println("Nanny deleted successfully");
    }
}