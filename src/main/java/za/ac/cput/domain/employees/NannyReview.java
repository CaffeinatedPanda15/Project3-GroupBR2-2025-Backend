package za.ac.cput.domain.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.ac.cput.domain.parent.Parent;

import java.sql.Timestamp;

@Entity
public class NannyReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nannyId")
    private Nanny nanny;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Parent parent;

    private int rating;
    private String comments;
    private Timestamp reviewDate;

    protected NannyReview() {}

    private NannyReview(Builder builder) {
        this.reviewId = builder.reviewId;
        this.nanny = builder.nanny;
        this.parent = builder.parent;
        this.rating = builder.rating;
        this.comments = builder.comments;
        this.reviewDate = builder.reviewDate;
    }

    public int getReviewId() { return reviewId; }
    public Nanny getNanny() { return nanny; }
    public Parent getParent() { return parent; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public Timestamp getReviewDate() { return reviewDate; }

    @Override
    public String toString() {
        return "NannyReview{" +
                "reviewId=" + reviewId +
                ", nanny=" + nanny +
                ", parent=" + parent +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }

    public static class Builder {
        private int reviewId;
        private Nanny nanny;
        private Parent parent;
        private int rating;
        private String comments;
        private Timestamp reviewDate;

        public Builder setReviewId(int reviewId) { this.reviewId = reviewId; return this; }
        public Builder setNanny(Nanny nanny) { this.nanny = nanny; return this; }
        public Builder setParent(Parent parent) { this.parent = parent; return this; }
        public Builder setRating(int rating) { this.rating = rating; return this; }
        public Builder setComments(String comments) { this.comments = comments; return this; }
        public Builder setReviewDate(Timestamp reviewDate) { this.reviewDate = reviewDate; return this; }

        public NannyReview build() { return new NannyReview(this); }

        public Builder copy(NannyReview review) {
            this.reviewId = review.reviewId;
            this.nanny = review.nanny;
            this.parent = review.parent;
            this.rating = review.rating;
            this.comments = review.comments;
            this.reviewDate = review.reviewDate;
            return this;
        }
    }//end of Builder class
}//end of class
