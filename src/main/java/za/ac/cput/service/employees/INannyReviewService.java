package za.ac.cput.service.employees;

import za.ac.cput.domain.employees.NannyReview;
import za.ac.cput.service.IService;
import java.util.List;

public interface INannyReviewService extends IService<NannyReview, Integer> {
    void delete(Integer id);
    List<NannyReview> getReviewsByNanny(Integer nannyId);
    List<NannyReview> getReviewsByParent(Integer parentId);
}
