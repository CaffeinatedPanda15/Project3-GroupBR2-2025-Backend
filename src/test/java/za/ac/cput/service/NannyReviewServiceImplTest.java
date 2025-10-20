package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.employees.NannyReview;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.factories.employees.NannyFactory;
import za.ac.cput.factories.employees.NannyReviewFactory;
import za.ac.cput.factories.parent.ParentFactory;
import za.ac.cput.service.employees.INannyReviewService;
import za.ac.cput.service.employees.INannyService;
import za.ac.cput.service.parent.IParentService;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NannyReviewServiceImplTest {

    @Autowired
    private INannyReviewService nannyReviewService;

    @Autowired
    private INannyService nannyService;

    @Autowired
    private IParentService parentService;

    private static NannyReview review1;
    private static NannyReview review2;
    private static Nanny nanny;
    private static Parent parent;

    @BeforeAll
    static void setUp() {
        // Will create in test method due to dependencies
    }

    @Test
    @Order(1)
    void create() {
        // Create nanny and parent first
        nanny = NannyFactory.createNanny("Catherine", "Smith", new HashSet<>(), new HashSet<>());
        nanny = nannyService.create(nanny);
        assertNotNull(nanny);

        parent = ParentFactory.createParent("David", "Brown", new HashSet<>(), new HashSet<>());
        parent = parentService.create(parent);
        assertNotNull(parent);

        // Create reviews
        review1 = NannyReviewFactory.createNannyReview(
                nanny, 
                parent, 
                5, 
                "Excellent service!", 
                new Timestamp(System.currentTimeMillis())
        );
        NannyReview created1 = nannyReviewService.create(review1);
        assertNotNull(created1);
        assertEquals(5, created1.getRating());
        System.out.println("Created Nanny Review 1: " + created1);

        review2 = NannyReviewFactory.createNannyReview(
                nanny, 
                parent, 
                4, 
                "Very good care", 
                new Timestamp(System.currentTimeMillis())
        );
        NannyReview created2 = nannyReviewService.create(review2);
        assertNotNull(created2);
        assertEquals(4, created2.getRating());
        System.out.println("Created Nanny Review 2: " + created2);
    }

    @Test
    @Order(2)
    void read() {
        NannyReview read = nannyReviewService.read(1);
        assertNotNull(read);
        assertEquals(5, read.getRating());
        System.out.println("Read Nanny Review: " + read);
    }

    @Test
    @Order(3)
    void update() {
        NannyReview existing = nannyReviewService.read(1);
        assertNotNull(existing);

        NannyReview updated = new NannyReview.Builder()
                .copy(existing)
                .setComments("Outstanding service!")
                .build();

        NannyReview result = nannyReviewService.update(updated);
        assertNotNull(result);
        assertEquals("Outstanding service!", result.getComments());
        System.out.println("Updated Nanny Review: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<NannyReview> reviews = nannyReviewService.getAll();
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.size() >= 2);
        System.out.println("All Nanny Reviews: " + reviews);
    }

    @Test
    @Order(5)
    void getReviewsByNanny() {
        List<NannyReview> reviews = nannyReviewService.getReviewsByNanny(nanny.getNannyId());
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(2, reviews.size());
        System.out.println("Reviews by Nanny: " + reviews);
    }

    @Test
    @Order(6)
    void getReviewsByParent() {
        List<NannyReview> reviews = nannyReviewService.getReviewsByParent(parent.getParentId());
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(2, reviews.size());
        System.out.println("Reviews by Parent: " + reviews);
    }

    @Test
    @Order(7)
    void delete() {
        NannyReview toDelete = nannyReviewService.read(2);
        assertNotNull(toDelete);

        nannyReviewService.delete(2);
        NannyReview deleted = nannyReviewService.read(2);
        assertNull(deleted);
        System.out.println("Nanny Review deleted successfully");
    }
}