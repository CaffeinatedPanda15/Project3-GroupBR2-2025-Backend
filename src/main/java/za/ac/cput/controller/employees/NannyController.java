package za.ac.cput.controller.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.service.employees.INannyService;

import java.util.List;

@RestController
@RequestMapping("/api/nanny")
@CrossOrigin(originPatterns = "*")
public class NannyController {

    private final INannyService nannyService;

    @Autowired
    public NannyController(INannyService nannyService) {
        this.nannyService = nannyService;
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
    public ResponseEntity<List<Nanny>> getAll() {
        List<Nanny> nannies = nannyService.getAll();
        return new ResponseEntity<>(nannies, HttpStatus.OK);
    }
}
