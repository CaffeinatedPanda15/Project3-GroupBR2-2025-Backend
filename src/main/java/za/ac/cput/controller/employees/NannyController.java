package za.ac.cput.controller.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.SessionStatus;
import za.ac.cput.repositories.parent.IChildSittingSessionRepository;
import za.ac.cput.service.employees.INannyService;

import java.util.*;

@RestController
@RequestMapping("/api/nanny")
@CrossOrigin(originPatterns = "*")
public class NannyController {

    private final INannyService nannyService;
    private final IChildSittingSessionRepository sessionRepository;

    @Autowired
    public NannyController(INannyService nannyService, IChildSittingSessionRepository sessionRepository) {
        this.nannyService = nannyService;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Nanny> create(@RequestBody Nanny nanny) {
        try {
            Nanny created = nannyService.create(nanny);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Nanny> read(@PathVariable Integer id) {
        Nanny nanny = nannyService.read(id);
        if (nanny != null) {
            return new ResponseEntity<>(nanny, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Nanny> update(@RequestBody Nanny nanny) {
        try {
            Nanny updated = nannyService.update(nanny);
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
            nannyService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        try {
            List<Nanny> nannies = nannyService.getAll();
            List<Map<String, Object>> nannyList = new ArrayList<>();
            
            for (Nanny nanny : nannies) {
                Map<String, Object> nannyData = new HashMap<>();
                nannyData.put("nannyId", nanny.getNannyId());
                nannyData.put("nannyName", nanny.getNannyName());
                nannyData.put("nannySurname", nanny.getNannySurname());
                nannyData.put("email", nanny.getEmail());
                nannyList.add(nannyData);
            }
            
            return new ResponseEntity<>(nannyList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching nannies: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // NEW ENDPOINT: Get nanny's assigned sessions
    @GetMapping("/sessions/{nannyId}")
    public ResponseEntity<List<Map<String, Object>>> getNannySessions(@PathVariable int nannyId) {
        try {
            Nanny nanny = nannyService.read(nannyId);
            if (nanny == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<ChildSittingSession> sessions = sessionRepository.findByNanny_NannyId(nannyId);
            List<Map<String, Object>> sessionList = new ArrayList<>();

            for (ChildSittingSession session : sessions) {
                Map<String, Object> sessionData = new HashMap<>();
                sessionData.put("id", session.getSessionId());
                sessionData.put("sessionDate", session.getSessionDate());
                sessionData.put("startTime", session.getSessionStartTime());
                sessionData.put("endTime", session.getSessionEndTime());
                sessionData.put("status", session.isSessionConfirmed() ? "Confirmed" : "Pending");
                
                // Get driver info if assigned
                if (session.getDriver() != null) {
                    sessionData.put("driverName", session.getDriver().getDriverName() + " " + session.getDriver().getDriverSurname());
                }
                
                // Get children info
                List<String> childrenNames = new ArrayList<>();
                for (var childSession : session.getChildSessions()) {
                    var child = childSession.getChild();
                    childrenNames.add(child.getChildName() + " " + child.getChildSurname());
                }
                sessionData.put("children", childrenNames);
                
                // Get parent info
                if (!session.getChildSessions().isEmpty()) {
                    var parent = session.getChildSessions().iterator().next().getChild().getParent();
                    sessionData.put("parentName", parent.getParentName() + " " + parent.getParentSurname());
                }
                
                sessionList.add(sessionData);
            }

            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching nanny sessions: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // NEW ENDPOINT: Get nanny's sessions by status
    @GetMapping("/sessions/{nannyId}/status/{status}")
    public ResponseEntity<List<Map<String, Object>>> getNannySessionsByStatus(
            @PathVariable int nannyId, 
            @PathVariable String status) {
        try {
            Nanny nanny = nannyService.read(nannyId);
            if (nanny == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            SessionStatus sessionStatus;
            try {
                sessionStatus = SessionStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<ChildSittingSession> sessions = sessionRepository.findByNanny_NannyIdAndStatus(nannyId, sessionStatus);
            List<Map<String, Object>> sessionList = new ArrayList<>();

            for (ChildSittingSession session : sessions) {
                Map<String, Object> sessionData = new HashMap<>();
                sessionData.put("id", session.getSessionId());
                sessionData.put("sessionDate", session.getSessionDate());
                sessionData.put("startTime", session.getSessionStartTime());
                sessionData.put("endTime", session.getSessionEndTime());
                sessionData.put("status", session.getStatus().toString());
                
                // Get driver info if assigned
                if (session.getDriver() != null) {
                    sessionData.put("driverName", session.getDriver().getDriverName() + " " + session.getDriver().getDriverSurname());
                }
                
                // Get children info
                List<String> childrenNames = new ArrayList<>();
                for (var childSession : session.getChildSessions()) {
                    var child = childSession.getChild();
                    childrenNames.add(child.getChildName() + " " + child.getChildSurname());
                }
                sessionData.put("children", childrenNames);
                
                sessionList.add(sessionData);
            }

            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching nanny sessions by status: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
