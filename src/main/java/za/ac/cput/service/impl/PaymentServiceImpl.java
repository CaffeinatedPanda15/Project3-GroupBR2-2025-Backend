package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Payment;
import za.ac.cput.repositories.IPaymentRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(Integer id) {
        return paymentRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        paymentRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<Payment> getPaymentsByParent(Integer parentId) {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getParent() != null && p.getParent().getParentId() == parentId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Payment> getPaymentsBySession(Integer sessionId) {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getSession() != null && p.getSession().getSessionId() == sessionId)
                .collect(Collectors.toList());
    }
}