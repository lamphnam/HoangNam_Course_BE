package com.hoangnam25.hnam_courseware.specification;

import com.hoangnam25.hnam_courseware.model.entity.Course;
import com.hoangnam25.hnam_courseware.model.enums.CourseDifficulty;
import com.hoangnam25.hnam_courseware.model.enums.CourseStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class CourseSpecification {
    public static Specification<Course> hasTitleLike(String keyword) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(keyword)) return null;
            return cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Course> hasDifficulty(CourseDifficulty difficulty) {
        return (root, query, cb) -> {
            if (difficulty == null) return null;
            return cb.equal(root.get("difficulty"), difficulty);
        };
    }

    public static Specification<Course> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("price"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.lessThanOrEqualTo(root.get("price"), max);
        };
    }

    public static Specification<Course> hasMinRating(BigDecimal minRating) {
        return (root, query, cb) -> {
            if (minRating == null) return null;
            return cb.greaterThanOrEqualTo(root.get("averageRating"), minRating);
        };
    }
    public static Specification<Course> hasStatus(CourseStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
