package com.hoangnam25.hnam_courseware.specification;

import com.hoangnam25.hnam_courseware.model.entity.Enrollment;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EnrollmentSpecification {
    public static Specification<Enrollment> belongsToUser(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }
    public static Specification<Enrollment> hasStatus(EnrollmentStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }
    public static Specification<Enrollment> courseTitleContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return null;
            Join<Object, Object> courseJoin = root.join("course", JoinType.INNER);
            return cb.like(cb.lower(courseJoin.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }
    public static Specification<Enrollment> enrolledAfter(LocalDateTime enrolledDate) {
        return (root, query, cb) -> {
            if (enrolledDate == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("enrolledDate"), enrolledDate);
        };
    }
    public static Specification<Enrollment> hasProgressGreaterThan(BigDecimal progressPercentage) {
        return (root, query, cb) -> {
            if (progressPercentage == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("progressPercentage"), progressPercentage);
        };
    }



}
