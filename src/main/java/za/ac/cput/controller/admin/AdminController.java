package za.ac.cput.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.admin.Admin;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.service.admin.IAdminService;
import za.ac.cput.service.employees.IDriverService;
import za.ac.cput.service.employees.INannyService;
import za.ac.cput.service.parent.IChildSittingSessionService;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(originPatterns = "*")
public class AdminController {

    private final IAdminService adminService;
    private final IChildSittingSessionService sessionService;
    private final IDriverService driverService;
    private final INannyService nannyService;

    @Autowired
    public AdminController(IAdminService adminService,
            IChildSittingSessionService sessionService,
            IDriverService driverService,
            INannyService nannyService) {
        this.adminService = adminService;
        this.sessionService = sessionService;
        this.driverService = driverService;
        this.nannyService = nannyService;
    }

    // Admin Authentication
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            if (username == null || password == null) {
                response.put("success", false);
                response.put("message", "Username and password are required");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Admin admin = adminService.authenticate(username, password);
            if (admin != null && admin.isActive()) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("adminId", admin.getAdminId());
                response.put("username", admin.getUsername());
                response.put("fullName", admin.getFullName());
                response.put("email", admin.getEmail());
                System.out.println("Admin login successful: " + username);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials or account disabled");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.err.println("Error during admin login: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all sessions for admin dashboard
    @GetMapping("/sessions")
    public ResponseEntity<List<Map<String, Object>>> getAllSessions() {
        try {
            List<ChildSittingSession> sessions = sessionService.getAll();
            List<Map<String, Object>> sessionList = new ArrayList<>();

            for (ChildSittingSession session : sessions) {
                Map<String, Object> sessionData = new HashMap<>();
                sessionData.put("sessionId", session.getSessionId());
                sessionData.put("sessionDate", session.getSessionDate());
                sessionData.put("startTime", session.getSessionStartTime());
                sessionData.put("endTime", session.getSessionEndTime());
                sessionData.put("status", session.getStatus().toString());
                sessionData.put("confirmed", session.isSessionConfirmed());

                // Add nanny info
                if (session.getNanny() != null) {
                    sessionData.put("nannyId", session.getNanny().getNannyId());
                    sessionData.put("nannyName",
                            session.getNanny().getNannyName() + " " + session.getNanny().getNannySurname());
                }

                // Add driver info
                if (session.getDriver() != null) {
                    sessionData.put("driverId", session.getDriver().getDriverId());
                    sessionData.put("driverName",
                            session.getDriver().getDriverName() + " " + session.getDriver().getDriverSurname());
                }

                // Add children count
                sessionData.put("childrenCount", session.getChildSessions().size());

                sessionList.add(sessionData);
            }

            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching sessions for admin: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a session
    @DeleteMapping("/sessions/{sessionId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteSession(@PathVariable int sessionId) {
        Map<String, Object> response = new HashMap<>();
        try {
            ChildSittingSession session = sessionService.read(sessionId);
            if (session == null) {
                response.put("success", false);
                response.put("message", "Session not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            sessionService.delete(sessionId);
            response.put("success", true);
            response.put("message", "Session deleted successfully");
            response.put("sessionId", sessionId);
            System.out.println("Admin deleted session: " + sessionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error deleting session: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Failed to delete session: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all drivers
    @GetMapping("/drivers")
    public ResponseEntity<List<Map<String, Object>>> getAllDrivers() {
        try {
            List<Driver> drivers = driverService.getAll();
            List<Map<String, Object>> driverList = new ArrayList<>();

            for (Driver driver : drivers) {
                Map<String, Object> driverData = new HashMap<>();
                driverData.put("driverId", driver.getDriverId());
                driverData.put("driverName", driver.getDriverName());
                driverData.put("driverSurname", driver.getDriverSurname());
                driverData.put("email", driver.getEmail());
                // Note: Driver entity doesn't have phoneNumber or licenseNumber fields
                driverData.put("fullName", driver.getDriverName() + " " + driver.getDriverSurname());
                driverList.add(driverData);
            }

            return new ResponseEntity<>(driverList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching drivers for admin: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a driver
    @DeleteMapping("/drivers/{driverId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteDriver(@PathVariable int driverId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Driver driver = driverService.read(driverId);
            if (driver == null) {
                response.put("success", false);
                response.put("message", "Driver not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            driverService.delete(driverId);
            response.put("success", true);
            response.put("message", "Driver deleted successfully");
            response.put("driverId", driverId);
            System.out.println("Admin deleted driver: " + driverId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error deleting driver: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Failed to delete driver: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all nannies
    @GetMapping("/nannies")
    public ResponseEntity<List<Map<String, Object>>> getAllNannies() {
        try {
            List<Nanny> nannies = nannyService.getAll();
            List<Map<String, Object>> nannyList = new ArrayList<>();

            for (Nanny nanny : nannies) {
                Map<String, Object> nannyData = new HashMap<>();
                nannyData.put("nannyId", nanny.getNannyId());
                nannyData.put("nannyName", nanny.getNannyName());
                nannyData.put("nannySurname", nanny.getNannySurname());
                nannyData.put("email", nanny.getEmail());
                // Note: Nanny entity doesn't have direct phoneNumber or experience fields
                nannyData.put("fullName", nanny.getNannyName() + " " + nanny.getNannySurname());
                nannyData.put("contactsCount", nanny.getContacts().size());
                nannyData.put("reviewsCount", nanny.getReviews().size());
                nannyList.add(nannyData);
            }

            return new ResponseEntity<>(nannyList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching nannies for admin: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a nanny
    @DeleteMapping("/nannies/{nannyId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteNanny(@PathVariable int nannyId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Nanny nanny = nannyService.read(nannyId);
            if (nanny == null) {
                response.put("success", false);
                response.put("message", "Nanny not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            nannyService.delete(nannyId);
            response.put("success", true);
            response.put("message", "Nanny deleted successfully");
            response.put("nannyId", nannyId);
            System.out.println("Admin deleted nanny: " + nannyId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error deleting nanny: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Failed to delete nanny: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get admin dashboard statistics
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            // Count totals
            stats.put("totalSessions", sessionService.getAll().size());
            stats.put("totalDrivers", driverService.getAll().size());
            stats.put("totalNannies", nannyService.getAll().size());

            // Count sessions by status
            List<ChildSittingSession> allSessions = sessionService.getAll();
            long upcomingSessions = allSessions.stream().filter(s -> s.getStatus().toString().equals("UPCOMING"))
                    .count();
            long activeSessions = allSessions.stream().filter(s -> s.getStatus().toString().equals("ACTIVE")).count();
            long completedSessions = allSessions.stream().filter(s -> s.getStatus().toString().equals("COMPLETED"))
                    .count();

            stats.put("upcomingSessions", upcomingSessions);
            stats.put("activeSessions", activeSessions);
            stats.put("completedSessions", completedSessions);

            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching dashboard stats: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}