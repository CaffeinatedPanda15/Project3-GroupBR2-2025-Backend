package za.ac.cput.repositories.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.employees.NannyReview;

public interface INannyReviewRepository extends JpaRepository<NannyReview, Integer> {
}
