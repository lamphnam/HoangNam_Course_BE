package com.hoangnam25.hnam_courseware.model.entity;

import com.hoangnam25.hnam_courseware.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Size(max = 255)
    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdDate;

}
