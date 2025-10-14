package za.ac.cput.factories.employees;

import za.ac.cput.domain.employees.NannyReview;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Parent;

import java.sql.Timestamp;

public class NannyReviewFactory {

    public static NannyReview createNannyReview(Nanny nanny, Parent parent, int rating, String comments, Timestamp reviewDate) {
        return new NannyReview.Builder()
                .setNanny(nanny)
                .setParent(parent)
                .setRating(rating)
                .setComments(comments)
                .setReviewDate(reviewDate)
                .build();
    }
}
