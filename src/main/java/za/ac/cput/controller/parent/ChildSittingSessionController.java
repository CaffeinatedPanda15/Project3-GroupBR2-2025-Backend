package za.ac.cput.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.domain.parent.SessionStatus;
import za.ac.cput.repositories.IPaymentRepository;
import za.ac.cput.repositories.employees.IDriverRepository;
import za.ac.cput.repositories.employees.INannyRepository;
import za.ac.cput.repositories.parent.IChildRepository;
import za.ac.cput.repositories.parent.IChildSessionRepository;
import za.ac.cput.repositories.parent.IParentRepository;
import za.ac.cput.service.parent.IChildSittingSessionService;

import java.sql.Time;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

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

    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> bookSession(@RequestBody Map<String, Object> sessionRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("=== BOOKING SESSION REQUEST ===");
            System.out.println("Request data: " + sessionRequest);

            // Extract data from request
            int parentId = (Integer) sessionRequest.get("parentId");
            @SuppressWarnings("unchecked")
            List<Integer> childIds = (List<Integer>) sessionRequest.get("childIds");
            String sessionDate = (String) sessionRequest.get("sessionDate");
            String sessionStartTime = (String) sessionRequest.get("sessionStartTime");
            String sessionEndTime = (String) sessionRequest.get("sessionEndTime");
            int nannyId = (Integer) sessionRequest.get("nannyId");
            int driverId = (Integer) sessionRequest.get("driverId");
            double paymentAmount = ((Number) sessionRequest.get("paymentAmount")).doubleValue();

            System.out.println("Parent ID: " + parentId);
            System.out.println("Child IDs: " + childIds);
            System.out.println("Session Date: " + sessionDate);
            System.out.println("Start Time: " + sessionStartTime);
            System.out.println("End Time: " + sessionEndTime);
            System.out.println("Nanny ID: " + nannyId);
            System.out.println("Driver ID: " + driverId);
            System.out.println("Payment Amount: " + paymentAmount);

            // Get entities
            Parent parent = parentRepository.findById((long) parentId).orElse(null);
            Nanny nanny = nannyRepository.findById(nannyId).orElse(null);
            Driver driver = driverRepository.findById((long) driverId).orElse(null);

            if (parent == null || nanny == null || driver == null) {
                response.put("success", false);
                response.put("message", "Parent, Nanny, or Driver not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Create session
            ChildSittingSession session = new ChildSittingSession.Builder()
                    .setSessionDate(java.sql.Date.valueOf(sessionDate))
                    .setSessionStartTime(Time.valueOf(sessionStartTime))
                    .setSessionEndTime(Time.valueOf(sessionEndTime))
                    .setNanny(nanny)
                    .setDriver(driver)
                    .setStatus(SessionStatus.UPCOMING)
                    .setSessionConfirmed(true)
                    .build();

            // Save session first
            ChildSittingSession savedSession = childSittingSessionService.create(session);
            System.out.println("Session created with ID: " + savedSession.getSessionId());

            // Create child sessions
            Set<ChildSession> childSessions = new HashSet<>();
            for (Integer childId : childIds) {
                Child child = childRepository.findById(childId).orElse(null);
                if (child != null) {
                    ChildSession childSession = new ChildSession.Builder()
                            .setSession(savedSession)
                            .setChild(child)
                            .build();
                    ChildSession savedChildSession = childSessionRepository.save(childSession);
                    childSessions.add(savedChildSession);
                    System.out.println("Child session created for child ID: " + childId);
                }
            }

            // Child sessions are automatically linked via JPA relationship
            // No need to manually set them on the parent entity

            // Create payment
            Payment payment = new Payment.Builder()
                    .setAmount(paymentAmount)
                    .setTimeStamp((int) (System.currentTimeMillis() / 1000)) // Convert to timestamp
                    .setParent(parent)
                    .setSession(savedSession)
                    .build();

            Payment savedPayment = paymentRepository.save(payment);
            System.out.println("Payment created with ID: " + savedPayment.getPaymentId());

            // Prepare response
            response.put("success", true);
            response.put("message", "Session booked successfully");
            response.put("sessionId", savedSession.getSessionId());
            response.put("paymentId", savedPayment.getPaymentId());
            response.put("nannyName", nanny.getNannyName() + " " + nanny.getNannySurname());
            response.put("driverName", driver.getDriverName() + " " + driver.getDriverSurname());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Error booking session: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to book session: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Activate a session (change status from UPCOMING to ACTIVE)
    @PutMapping("/activate/{sessionId}")
    public ResponseEntity<Map<String, Object>> activateSession(@PathVariable int sessionId) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("=== ACTIVATING SESSION " + sessionId + " ===");

            // First check if session exists
            ChildSittingSession existingSession = childSittingSessionService.read(sessionId);
            if (existingSession == null) {
                System.out.println("Session not found: " + sessionId);
                response.put("success", false);
                response.put("message", "Session not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            System.out.println("Current session status: " + existingSession.getStatus());

            ChildSittingSession session = childSittingSessionService.activateSession(sessionId);
            if (session != null) {
                System.out.println("Session activated successfully. New status: " + session.getStatus());
                response.put("success", true);
                response.put("message", "Session activated successfully");
                response.put("sessionId", session.getSessionId());
                response.put("status", session.getStatus().toString());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                System.out.println("Failed to activate session");
                response.put("success", false);
                response.put("message", "Failed to activate session");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            System.err.println("Error activating session: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to activate session: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Complete a session (change status from ACTIVE to COMPLETED)
    @PutMapping("/complete/{sessionId}")
    public ResponseEntity<Map<String, Object>> completeSession(@PathVariable int sessionId) {
        Map<String, Object> response = new HashMap<>();
        try {
            ChildSittingSession session = childSittingSessionService.completeSession(sessionId);
            if (session != null) {
                response.put("success", true);
                response.put("message", "Session completed successfully");
                response.put("sessionId", session.getSessionId());
                response.put("status", session.getStatus().toString());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Session not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to complete session: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Test endpoint to see if service is working
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        try {
            List<ChildSittingSession> allSessions = childSittingSessionService.getAll();
            return new ResponseEntity<>("Found " + allSessions.size() + " total sessions", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all sessions for a specific parent
    @GetMapping("/parent/{parentId}")
    @Transactional
    public ResponseEntity<List<Map<String, Object>>> getParentSessions(@PathVariable int parentId) {
        try {
            System.out.println("=== FETCHING PARENT SESSIONS ===");
            System.out.println("Parent ID: " + parentId);

            // Use the repository method that uses JPQL with proper joins
            List<ChildSittingSession> sessions;
            try {
                // For now, let's use a simpler approach and filter manually
                List<ChildSittingSession> allSessions = childSittingSessionService.getAll();
                sessions = new ArrayList<>();
                for (ChildSittingSession session : allSessions) {
                    // Check if any child in this session belongs to the specified parent
                    if (session.getChildSessions() != null) {
                        for (ChildSession childSession : session.getChildSessions()) {
                            if (childSession != null && childSession.getChild() != null &&
                                    childSession.getChild().getParent() != null &&
                                    childSession.getChild().getParent().getParentId() == parentId) {
                                sessions.add(session);
                                break; // Found one child for this parent, no need to check others
                            }
                        }
                    }
                }
                System.out.println("Found " + sessions.size() + " sessions for parent " + parentId);
            } catch (Exception e) {
                System.err.println("Error fetching sessions from service: " + e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<Map<String, Object>> sessionList = new ArrayList<>();

            for (ChildSittingSession session : sessions) {
                try {
                    System.out.println("Processing session: " + session.getSessionId());
                    Map<String, Object> sessionData = new HashMap<>();
                    sessionData.put("sessionId", session.getSessionId());
                    sessionData.put("sessionDate", session.getSessionDate());
                    sessionData.put("startTime", session.getSessionStartTime());
                    sessionData.put("endTime", session.getSessionEndTime());
                    sessionData.put("status", session.getStatus().toString());

                    // Add nanny information
                    if (session.getNanny() != null) {
                        sessionData.put("nannyName",
                                session.getNanny().getNannyName() + " " + session.getNanny().getNannySurname());
                        sessionData.put("nannyId", session.getNanny().getNannyId());
                        System.out.println("Nanny: " + session.getNanny().getNannyName());
                    }

                    // Add driver information
                    if (session.getDriver() != null) {
                        sessionData.put("driverName",
                                session.getDriver().getDriverName() + " " + session.getDriver().getDriverSurname());
                        sessionData.put("driverId", session.getDriver().getDriverId());
                        System.out.println("Driver: " + session.getDriver().getDriverName());
                    }

                    // Add children information - only for this parent
                    List<String> childrenNames = new ArrayList<>();
                    if (session.getChildSessions() != null) {
                        System.out.println("Child sessions count: " + session.getChildSessions().size());
                        for (ChildSession childSession : session.getChildSessions()) {
                            if (childSession != null && childSession.getChild() != null) {
                                Child child = childSession.getChild();
                                // Only include children that belong to this parent
                                if (child.getParent() != null && child.getParent().getParentId() == parentId) {
                                    childrenNames.add(child.getChildName() + " " + child.getChildSurname());
                                    System.out.println("Child: " + child.getChildName());
                                }
                            }
                        }
                    }
                    sessionData.put("children", childrenNames);

                    sessionList.add(sessionData);
                    System.out.println("Session data: " + sessionData);
                } catch (Exception e) {
                    System.err.println("Error processing session " + session.getSessionId() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("=== RETURNING " + sessionList.size() + " SESSIONS ===");
            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching parent sessions: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Debug endpoint to check all sessions
    @GetMapping("/debug/all")
    public ResponseEntity<List<Map<String, Object>>> getAllSessionsDebug() {
        try {
            List<ChildSittingSession> allSessions = childSittingSessionService.getAll();
            List<Map<String, Object>> sessionList = new ArrayList<>();

            System.out.println("=== DEBUG: ALL SESSIONS ===");
            System.out.println("Total sessions in database: " + allSessions.size());

            for (ChildSittingSession session : allSessions) {
                Map<String, Object> sessionData = new HashMap<>();
                sessionData.put("sessionId", session.getSessionId());
                sessionData.put("sessionDate", session.getSessionDate());
                sessionData.put("status", session.getStatus().toString());

                try {
                    sessionData.put("childSessionsCount", session.getChildSessions().size());
                } catch (Exception e) {
                    sessionData.put("childSessionsCount", "Error loading: " + e.getMessage());
                }

                sessionList.add(sessionData);
            }

            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error in debug endpoint: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}