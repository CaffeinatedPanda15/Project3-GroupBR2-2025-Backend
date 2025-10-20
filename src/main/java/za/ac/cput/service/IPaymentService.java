package za.ac.cput.service;

import za.ac.cput.domain.Payment;
import java.util.List;

public interface IPaymentService extends IService<Payment, Integer> {
    void delete(Integer id);
    List<Payment> getPaymentsByParent(Integer parentId);
    List<Payment> getPaymentsBySession(Integer sessionId);
}