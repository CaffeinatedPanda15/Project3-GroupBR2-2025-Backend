package za.ac.cput.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.service.parent.IParentService;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
@CrossOrigin(origins = "*")
public class ParentController {

    private final IParentService parentService;

    @Autowired
    public ParentController(IParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Parent> create(@RequestBody Parent parent) {
        try {
            Parent created = parentService.create(parent);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Parent> read(@PathVariable Integer id) {
        Parent parent = parentService.read(id);
        if (parent != null) {
            return new ResponseEntity<>(parent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Parent> update(@RequestBody Parent parent) {
        try {
            Parent updated = parentService.update(parent);
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
            parentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parent>> getAll() {
        List<Parent> parents = parentService.getAll();
        return new ResponseEntity<>(parents, HttpStatus.OK);
    }
}
