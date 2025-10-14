package za.ac.cput.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {
}
