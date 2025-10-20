package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.service.IPaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        try {
            Payment created = paymentService.create(payment);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Payment> read(@PathVariable Integer id) {
        Payment payment = paymentService.read(id);
        if (payment != null) {
            return new ResponseEntity<>(payment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Payment> update(@RequestBody Payment payment) {
        try {
            Payment updated = paymentService.update(payment);
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
            paymentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAll() {
        List<Payment> payments = paymentService.getAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Payment>> getByParentId(@PathVariable Integer parentId) {
        List<Payment> payments = paymentService.getPaymentsByParent(parentId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Payment>> getBySessionId(@PathVariable Integer sessionId) {
        List<Payment> payments = paymentService.getPaymentsBySession(sessionId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
