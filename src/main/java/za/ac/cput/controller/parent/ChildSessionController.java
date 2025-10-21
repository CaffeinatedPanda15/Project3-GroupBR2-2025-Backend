package za.ac.cput.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.service.parent.IChildSessionService;

import java.util.List;

@RestController
@RequestMapping("/api/child-session")
@CrossOrigin(originPatterns = "*")
public class ChildSessionController {

    private final IChildSessionService childSessionService;

    @Autowired
    public ChildSessionController(IChildSessionService childSessionService) {
        this.childSessionService = childSessionService;
    }

    @PostMapping("/create")
    public ResponseEntity<ChildSession> create(@RequestBody ChildSession childSession) {
        try {
            ChildSession created = childSessionService.create(childSession);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ChildSession> read(@PathVariable Integer id) {
        ChildSession childSession = childSessionService.read(id);
        if (childSession != null) {
            return new ResponseEntity<>(childSession, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<ChildSession> update(@RequestBody ChildSession childSession) {
        try {
            ChildSession updated = childSessionService.update(childSession);
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
            childSessionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChildSession>> getAll() {
        List<ChildSession> sessions = childSessionService.getAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ChildSession>> getByChildId(@PathVariable Integer childId) {
        List<ChildSession> sessions = childSessionService.getSessionsByChild(childId);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}
