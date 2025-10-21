package za.ac.cput.controller.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.employees.NannyReview;
import za.ac.cput.service.employees.INannyReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/nanny-review")
@CrossOrigin(originPatterns = "*")
public class NannyReviewController {

    private final INannyReviewService nannyReviewService;

    @Autowired
    public NannyReviewController(INannyReviewService nannyReviewService) {
        this.nannyReviewService = nannyReviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<NannyReview> create(@RequestBody NannyReview nannyReview) {
        try {
            NannyReview created = nannyReviewService.create(nannyReview);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<NannyReview> read(@PathVariable Integer id) {
        NannyReview nannyReview = nannyReviewService.read(id);
        if (nannyReview != null) {
            return new ResponseEntity<>(nannyReview, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<NannyReview> update(@RequestBody NannyReview nannyReview) {
        try {
            NannyReview updated = nannyReviewService.update(nannyReview);
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
            nannyReviewService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<NannyReview>> getAll() {
        List<NannyReview> reviews = nannyReviewService.getAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/nanny/{nannyId}")
    public ResponseEntity<List<NannyReview>> getByNannyId(@PathVariable Integer nannyId) {
        List<NannyReview> reviews = nannyReviewService.getReviewsByNanny(nannyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<NannyReview>> getByParentId(@PathVariable Integer parentId) {
        List<NannyReview> reviews = nannyReviewService.getReviewsByParent(parentId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
