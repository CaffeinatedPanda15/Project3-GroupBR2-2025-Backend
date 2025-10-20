package za.ac.cput.controller.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.service.employees.IBackgroundCheckService;

import java.util.List;

@RestController
@RequestMapping("/api/background-check")
@CrossOrigin(origins = "*")
public class BackgroundCheckController {

    private final IBackgroundCheckService backgroundCheckService;

    @Autowired
    public BackgroundCheckController(IBackgroundCheckService backgroundCheckService) {
        this.backgroundCheckService = backgroundCheckService;
    }

    @PostMapping("/create")
    public ResponseEntity<BackgroundCheck> create(@RequestBody BackgroundCheck backgroundCheck) {
        try {
            BackgroundCheck created = backgroundCheckService.create(backgroundCheck);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BackgroundCheck> read(@PathVariable Integer id) {
        BackgroundCheck backgroundCheck = backgroundCheckService.read(id);
        if (backgroundCheck != null) {
            return new ResponseEntity<>(backgroundCheck, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<BackgroundCheck> update(@RequestBody BackgroundCheck backgroundCheck) {
        try {
            BackgroundCheck updated = backgroundCheckService.update(backgroundCheck);
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
            backgroundCheckService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BackgroundCheck>> getAll() {
        List<BackgroundCheck> backgroundChecks = backgroundCheckService.getAll();
        return new ResponseEntity<>(backgroundChecks, HttpStatus.OK);
    }

    @GetMapping("/nanny/{nannyId}")
    public ResponseEntity<BackgroundCheck> getByNannyId(@PathVariable Integer nannyId) {
        BackgroundCheck backgroundCheck = backgroundCheckService.getByNannyId(nannyId);
        if (backgroundCheck != null) {
            return new ResponseEntity<>(backgroundCheck, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
