package com.hoangnam25.hnam_courseware.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "Review.withUserAndCourse",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("course")
        }
)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    private Integer rating;

    private String comment;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
