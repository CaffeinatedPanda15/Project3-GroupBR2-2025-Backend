package za.ac.cput.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.factories.PaymentFactory;
import za.ac.cput.factories.parent.ChildSittingSessionFactory;
import za.ac.cput.factories.parent.ParentFactory;
import za.ac.cput.service.IPaymentService;
import za.ac.cput.service.parent.IParentService;
import za.ac.cput.service.parent.IChildSittingSessionService;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceImplTest {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IParentService parentService;

    @Autowired
    private IChildSittingSessionService sessionService;

    private static Payment payment1;
    private static Payment payment2;
    private static Parent parent;
    private static ChildSittingSession session;

    @BeforeAll
    static void setUp() {
        // Will be created in the test methods due to dependencies
    }

    @Test
    @Order(1)
    void create() {
        // Create parent first
        parent = ParentFactory.createParent("John", "Doe", new HashSet<>(), new HashSet<>());
        parent = parentService.create(parent);
        assertNotNull(parent);

        // Create session
        session = ChildSittingSessionFactory.createSession(
                new Date(), 
                Time.valueOf("09:00:00"), 
                Time.valueOf("17:00:00"), 
                true, 
                null, 
                null
        );
        session = sessionService.create(session);
        assertNotNull(session);

        // Create payments
        payment1 = PaymentFactory.createPayment(parent, session, 500.00, 1234567890);
        Payment created1 = paymentService.create(payment1);
        assertNotNull(created1);
        assertEquals(500.00, created1.getAmount());
        System.out.println("Created Payment 1: " + created1);

        payment2 = PaymentFactory.createPayment(parent, session, 750.00, 1234567891);
        Payment created2 = paymentService.create(payment2);
        assertNotNull(created2);
        assertEquals(750.00, created2.getAmount());
        System.out.println("Created Payment 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        Payment read = paymentService.read(1);
        assertNotNull(read);
        assertEquals(500.00, read.getAmount());

    }

    @Test
    @Order(3)
    void update() {
        Payment existing = paymentService.read(1);
        assertNotNull(existing);

        Payment updated = new Payment.Builder()
                .copy(existing)
                .setAmount(600.00)
                .build();

        Payment result = paymentService.update(updated);
        assertNotNull(result);
        assertEquals(600.00, result.getAmount());

    }

    @Test
    @Order(4)
    void getAll() {
        List<Payment> payments = paymentService.getAll();
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertTrue(payments.size() >= 2);
        System.out.println("All Payments: " + payments);
    }

    @Test
    @Order(5)
    void getPaymentsByParent() {
        List<Payment> payments = paymentService.getPaymentsByParent(parent.getParentId());
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        System.out.println("Payments by Parent: " + payments);
    }

    @Test
    @Order(6)
    void getPaymentsBySession() {
        List<Payment> payments = paymentService.getPaymentsBySession(session.getSessionId());
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        System.out.println("Payments by Session: " + payments);
    }

    @Test
    @Order(7)
    void delete() {
        Payment toDelete = paymentService.read(2);
        assertNotNull(toDelete);

        paymentService.delete(2);
        Payment deleted = paymentService.read(2);
        assertNull(deleted);
        System.out.println("Payment deleted successfully");
    }
}