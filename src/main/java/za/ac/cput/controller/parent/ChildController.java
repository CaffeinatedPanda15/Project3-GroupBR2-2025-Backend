package za.ac.cput.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.service.parent.IChildService;

import java.util.List;

@RestController
@RequestMapping("/api/child")
@CrossOrigin(originPatterns = "*")
public class ChildController {

    private final IChildService childService;

    @Autowired
    public ChildController(IChildService childService) {
        this.childService = childService;
    }

    @PostMapping("/create")
    public ResponseEntity<Child> create(@RequestBody Child child) {
        try {
            Child created = childService.create(child);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Child> read(@PathVariable Integer id) {
        Child child = childService.read(id);
        if (child != null) {
            return new ResponseEntity<>(child, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Child> update(@RequestBody Child child) {
        try {
            Child updated = childService.update(child);
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
            childService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Child>> getAll() {
        List<Child> children = childService.getAll();
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Child>> getByParentId(@PathVariable Integer parentId) {
        List<Child> children = childService.getChildrenByParent(parentId);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }
}
