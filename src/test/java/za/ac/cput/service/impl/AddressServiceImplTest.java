package za.ac.cput.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.factories.AddressFactory;
import za.ac.cput.service.IAddressService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceImplTest {

    @Autowired
    private IAddressService addressService;

    private static Address address1;
    private static Address address2;

    @BeforeAll
    static void setUp() {
        address1 = AddressFactory.createAddress(123, "Main Street", "Cape Town", "Western Cape", "8001");
        address2 = AddressFactory.createAddress(456, "Beach Road", "Durban", "KwaZulu-Natal", "4001");
    }

    @Test
    @Order(1)
    void create() {
        Address created1 = addressService.create(address1);
        assertNotNull(created1);
        assertEquals(address1.getStreetName(), created1.getStreetName());
        System.out.println("Created Address 1: " + created1);

        Address created2 = addressService.create(address2);
        assertNotNull(created2);
        assertEquals(address2.getCity(), created2.getCity());
        System.out.println("Created Address 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Address read = addressService.read(1);
        assertNotNull(read);
        assertEquals("Main Street", read.getStreetName());
        System.out.println("Read Address: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Address existing = addressService.read(1);
        assertNotNull(existing);

        Address updated = new Address.Builder()
                .setHouseNo(existing.getHouseNo())
                .setStreetName("Updated Street")
                .setCity(existing.getCity())
                .setProvince(existing.getProvince())
                .setPostalCode(existing.getPostalCode())
                .build();

        Address result = addressService.update(updated);
        assertNotNull(result);
        assertEquals("Updated Street", result.getStreetName());
        System.out.println("Updated Address: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Address> addresses = addressService.getAll();
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertTrue(addresses.size() >= 2);
        System.out.println("All Addresses: " + addresses);
    }

    @Test
    @Order(5)
    void delete() {
        Address toDelete = addressService.read(2);
        assertNotNull(toDelete);

        addressService.delete(2);
        Address deleted = addressService.read(2);
        assertNull(deleted);
        System.out.println("Address deleted successfully");
    }
}