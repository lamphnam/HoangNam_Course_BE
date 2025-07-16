package com.hoangnam25.hnam_courseware.specification;

import com.hoangnam25.hnam_courseware.model.entity.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public static Specification<Review> hasRating(Integer rating) {
        return (root, query, cb) -> {
            if (rating == null) return null;
            return cb.equal(root.get("rating"), rating);
        };
    }

    public static Specification<Review> belongsToCourse(Long courseId) {
        return (root, query, cb) -> cb.equal(root.get("course").get("id"), courseId);
    }
}

