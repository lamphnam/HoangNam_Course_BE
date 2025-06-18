package com.hoangnam25.hnam_courseware.entity;

import com.hoangnam25.hnam_courseware.entity.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Date;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @Email
    private String email;

    private Role role;

    private String urlImage;

    private Date createdDate;

}
