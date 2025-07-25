package com.hoangnam25.hnam_courseware.repository;

import com.hoangnam25.hnam_courseware.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
