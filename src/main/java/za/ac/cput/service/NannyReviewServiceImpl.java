package za.ac.cput.service.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.employees.NannyReview;
import za.ac.cput.repositories.employees.INannyReviewRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NannyReviewServiceImpl implements INannyReviewService {

    private final INannyReviewRepository reviewRepository;

    @Autowired
    public NannyReviewServiceImpl(INannyReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public NannyReview create(NannyReview review) {
        return reviewRepository.save(review);
    }

    @Override
    public NannyReview read(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public NannyReview update(NannyReview review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<NannyReview> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<NannyReview> getReviewsByNanny(Integer nannyId) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getNanny() != null && r.getNanny().getNannyId() == nannyId)
                .collect(Collectors.toList());
    }

    @Override
    public List<NannyReview> getReviewsByParent(Integer parentId) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getParent() != null && r.getParent().getParentId() == parentId)
                .collect(Collectors.toList());
    }
}