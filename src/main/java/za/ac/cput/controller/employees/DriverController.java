package za.ac.cput.controller.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.repositories.parent.IChildSittingSessionRepository;
import za.ac.cput.service.employees.IDriverService;

import java.util.*;

@RestController
@RequestMapping("/api/driver")
@CrossOrigin(originPatterns = "*")
public class DriverController {

    private final IDriverService driverService;
    private final IChildSittingSessionRepository sessionRepository;

    @Autowired
    public DriverController(IDriverService driverService, IChildSittingSessionRepository sessionRepository) {
        this.driverService = driverService;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Driver> create(@RequestBody Driver driver) {
        try {
            Driver created = driverService.create(driver);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Driver> read(@PathVariable Integer id) {
        Driver driver = driverService.read(id);
        if (driver != null) {
            return new ResponseEntity<>(driver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Driver> update(@RequestBody Driver driver) {
        try {
            Driver updated = driverService.update(driver);
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
            driverService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        try {
            List<Driver> drivers = driverService.getAll();
            List<Map<String, Object>> driverList = new ArrayList<>();
            
            for (Driver driver : drivers) {
                Map<String, Object> driverData = new HashMap<>();
                driverData.put("driverId", driver.getDriverId());
                driverData.put("driverName", driver.getDriverName());
                driverData.put("driverSurname", driver.getDriverSurname());
                driverData.put("email", driver.getEmail());
                driverList.add(driverData);
            }
            
            return new ResponseEntity<>(driverList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching drivers: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // NEW ENDPOINT: Get driver's assigned trips/sessions
    @GetMapping("/trips/{driverId}")
    public ResponseEntity<List<Map<String, Object>>> getDriverTrips(@PathVariable int driverId) {
        try {
            Driver driver = driverService.read(driverId);
            if (driver == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<ChildSittingSession> sessions = sessionRepository.findByDriver_DriverId(driverId);
            List<Map<String, Object>> trips = new ArrayList<>();

            for (ChildSittingSession session : sessions) {
                Map<String, Object> trip = new HashMap<>();
                trip.put("id", session.getSessionId());
                trip.put("sessionDate", session.getSessionDate());
                trip.put("startTime", session.getSessionStartTime());
                trip.put("endTime", session.getSessionEndTime());
                trip.put("status", session.isSessionConfirmed() ? "Confirmed" : "Pending");
                
                // Get nanny info
                if (session.getNanny() != null) {
                    trip.put("nannyName", session.getNanny().getNannyName() + " " + session.getNanny().getNannySurname());
                }
                
                // Get children info
                List<String> childrenNames = new ArrayList<>();
                for (var childSession : session.getChildSessions()) {
                    var child = childSession.getChild();
                    childrenNames.add(child.getChildName() + " " + child.getChildSurname());
                }
                trip.put("children", childrenNames);
                
                trips.add(trip);
            }

            return new ResponseEntity<>(trips, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching driver trips: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
