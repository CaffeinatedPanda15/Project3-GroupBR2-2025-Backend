package za.ac.cput.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.service.parent.IChildSittingSessionService;

import java.util.List;

@RestController
@RequestMapping("/api/child-sitting-session")
@CrossOrigin(origins = "*")
public class ChildSittingSessionController {

    private final IChildSittingSessionService childSittingSessionService;

    @Autowired
    public ChildSittingSessionController(IChildSittingSessionService childSittingSessionService) {
        this.childSittingSessionService = childSittingSessionService;
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
}
