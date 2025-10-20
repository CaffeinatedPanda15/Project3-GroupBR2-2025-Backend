package za.ac.cput.service.impl.employees;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.factories.employees.DriverFactory;
import za.ac.cput.service.employees.IDriverService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverServiceImplTest {

    @Autowired
    private IDriverService driverService;

    private static Driver driver1;
    private static Driver driver2;

    @BeforeAll
    static void setUp() {
        driver1 = DriverFactory.createDriver("James", "Bond");
        driver2 = DriverFactory.createDriver("Ethan", "Hunt");
    }

    @Test
    @Order(1)
    void create() {
        Driver created1 = driverService.create(driver1);
        assertNotNull(created1);
        assertEquals("James", created1.getDriverName());
        assertEquals("Bond", created1.getDriverSurname());
        System.out.println("Created Driver 1: " + created1);

        Driver created2 = driverService.create(driver2);
        assertNotNull(created2);
        assertEquals("Ethan", created2.getDriverName());
        System.out.println("Created Driver 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Driver read = driverService.read(1);
        assertNotNull(read);
        assertEquals("James", read.getDriverName());
        System.out.println("Read Driver: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Driver existing = driverService.read(1);
        assertNotNull(existing);

        Driver updated = new Driver.Builder()
                .copy(existing)
                .setDriverName("James Updated")
                .build();

        Driver result = driverService.update(updated);
        assertNotNull(result);
        assertEquals("James Updated", result.getDriverName());
        System.out.println("Updated Driver: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Driver> drivers = driverService.getAll();
        assertNotNull(drivers);
        assertFalse(drivers.isEmpty());
        assertTrue(drivers.size() >= 2);
        System.out.println("All Drivers: " + drivers);
    }

    @Test
    @Order(5)
    void delete() {
        Driver toDelete = driverService.read(2);
        assertNotNull(toDelete);

        driverService.delete(2);
        Driver deleted = driverService.read(2);
        assertNull(deleted);
        System.out.println("Driver deleted successfully");
    }
}