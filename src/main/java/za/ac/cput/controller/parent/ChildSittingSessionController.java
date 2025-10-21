package za.ac.cput.controller.parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.repositories.IPaymentRepository;
import za.ac.cput.repositories.employees.IDriverRepository;
import za.ac.cput.repositories.employees.INannyRepository;
import za.ac.cput.repositories.parent.IChildRepository;
import za.ac.cput.repositories.parent.IChildSessionRepository;
import za.ac.cput.repositories.parent.IParentRepository;
import za.ac.cput.service.parent.IChildSittingSessionService;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

@RestController
@RequestMapping("/api/child-sitting-session")
@CrossOrigin(originPatterns = "*")
public class ChildSittingSessionController {

    private final IChildSittingSessionService childSittingSessionService;
    private final IParentRepository parentRepository;
    private final IChildRepository childRepository;
    private final INannyRepository nannyRepository;
    private final IDriverRepository driverRepository;
    private final IChildSessionRepository childSessionRepository;
    private final IPaymentRepository paymentRepository;

    @Autowired
    public ChildSittingSessionController(IChildSittingSessionService childSittingSessionService,
                                        IParentRepository parentRepository,
                                        IChildRepository childRepository,
                                        INannyRepository nannyRepository,
                                        IDriverRepository driverRepository,
                                        IChildSessionRepository childSessionRepository,
                                        IPaymentRepository paymentRepository) {
        this.childSittingSessionService = childSittingSessionService;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.nannyRepository = nannyRepository;
        this.driverRepository = driverRepository;
        this.childSessionRepository = childSessionRepository;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<ChildSittingSession> create(@RequestBody ChildSittingSession session) {
        try {
            ChildSittingSession created = childSittingSessionService.create(session);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ChildSittingSession> read(@PathVariable Integer id) {
        ChildSittingSession session = childSittingSessionService.read(id);
        if (session != null) {
            return new ResponseEntity<>(session, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<ChildSittingSession> update(@RequestBody ChildSittingSession session) {
        try {
            ChildSittingSession updated = childSittingSessionService.update(session);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            childSittingSessionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChildSittingSession>> getAll() {
        List<ChildSittingSession> sessions = childSittingSessionService.getAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping("/confirmed")
    public ResponseEntity<List<ChildSittingSession>> getConfirmedSessions() {
        List<ChildSittingSession> sessions = childSittingSessionService.getConfirmedSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ChildSittingSession>> getPendingSessions() {
        List<ChildSittingSession> sessions = childSittingSessionService.getPendingSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    // NEW BOOKING ENDPOINT
    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookSession(@RequestBody SessionBookingRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            System.out.println("=== BOOKING SESSION REQUEST ===");
            System.out.println("Parent ID: " + request.getParentId());
            System.out.println("Child IDs: " + request.getChildIds());
            System.out.println("Session Date: " + request.getSessionDate());
            System.out.println("Start Time: " + request.getSessionStartTime());
            System.out.println("End Time: " + request.getSessionEndTime());
            System.out.println("Nanny ID: " + request.getNannyId());
            System.out.println("Driver ID: " + request.getDriverId());
            System.out.println("Payment Amount: " + request.getPaymentAmount());
            
            // 1. Validate parent exists
            Parent parent = parentRepository.findById((long) request.getParentId()).orElse(null);
            if (parent == null) {
                System.err.println("ERROR: Parent not found with ID: " + request.getParentId());
                response.put("success", false);
                response.put("message", "Parent not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            System.out.println("✓ Parent found: " + parent.getParentName());
            System.out.println("✓ Parent found: " + parent.getParentName());
            
            // 2. Validate children exist and belong to parent
            Set<Child> children = new HashSet<>();
            for (Integer childId : request.getChildIds()) {
                Child child = childRepository.findById(childId).orElse(null);
                if (child == null) {
                    System.err.println("ERROR: Child not found with ID: " + childId);
                    response.put("success", false);
                    response.put("message", "Child not found with ID: " + childId);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                if (child.getParent().getParentId() != parent.getParentId()) {
                    System.err.println("ERROR: Child " + childId + " does not belong to parent " + parent.getParentId());
                    response.put("success", false);
                    response.put("message", "Child " + childId + " does not belong to this parent");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                children.add(child);
                System.out.println("✓ Child found: " + child.getChildName());
            }
            
            // 3. Validate nanny exists
            Nanny nanny = nannyRepository.findById(request.getNannyId()).orElse(null);
            if (nanny == null) {
                System.err.println("ERROR: Nanny not found with ID: " + request.getNannyId());
                response.put("success", false);
                response.put("message", "Nanny not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            System.out.println("✓ Nanny found: " + nanny.getNannyName());
            System.out.println("✓ Nanny found: " + nanny.getNannyName());
            
            // 4. Validate driver if provided (optional)
            Driver driver = null;
            if (request.getDriverId() != null) {
                driver = driverRepository.findById((long) request.getDriverId()).orElse(null);
                if (driver == null) {
                    System.err.println("ERROR: Driver not found with ID: " + request.getDriverId());
                    response.put("success", false);
                    response.put("message", "Driver not found");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                System.out.println("✓ Driver found: " + driver.getDriverName());
            } else {
                System.out.println("✓ No driver assigned");
            }
            
            // 5. Create the session
            System.out.println("Creating session...");
            Time startTime = Time.valueOf(request.getSessionStartTime());
            Time endTime = Time.valueOf(request.getSessionEndTime());
            
            ChildSittingSession session = new ChildSittingSession.Builder()
                    .setSessionDate(request.getSessionDateAsDate())
                    .setSessionStartTime(startTime)
                    .setSessionEndTime(endTime)
                    .setSessionConfirmed(false) // Initially not confirmed
                    .setNanny(nanny)
                    .setDriver(driver)
                    .build();
            
            ChildSittingSession savedSession = childSittingSessionService.create(session);
            System.out.println("Session created with ID: " + savedSession.getSessionId());
            
            // 6. Link children to session
            for (Child child : children) {
                ChildSession childSession = new ChildSession.Builder()
                        .setChild(child)
                        .setSession(savedSession)
                        .build();
                childSessionRepository.save(childSession);
                System.out.println("Linked child " + child.getChildName() + " to session");
            }
            
            // 7. Create payment record
            Payment payment = new Payment.Builder()
                    .setAmount(request.getPaymentAmount())
                    .setTimeStamp((int) (System.currentTimeMillis() / 1000))
                    .setParent(parent)
                    .setSession(savedSession)
                    .build();
            
            Payment savedPayment = paymentRepository.save(payment);
            System.out.println("✓ Payment created with ID: " + savedPayment.getPaymentId());
            
            // 8. Build success response
            response.put("success", true);
            response.put("message", "Session booked successfully");
            response.put("sessionId", savedSession.getSessionId());
            response.put("paymentId", savedPayment.getPaymentId());
            response.put("nannyName", nanny.getNannyName() + " " + nanny.getNannySurname());
            if (driver != null) {
                response.put("driverName", driver.getDriverName() + " " + driver.getDriverSurname());
            }
            
            System.out.println("=== BOOKING SUCCESS ===");
            System.out.println("Session ID: " + savedSession.getSessionId());
            System.out.println("Payment ID: " + savedPayment.getPaymentId());
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid time format: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Invalid time format. Use HH:mm:ss");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error booking session: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to book session: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
