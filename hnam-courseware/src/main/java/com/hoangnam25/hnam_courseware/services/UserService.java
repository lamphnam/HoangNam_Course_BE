package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.converter.UserConverter;
import com.hoangnam25.hnam_courseware.model.dtos.UserResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    public UserResponseDto findMe(String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User is not exist");
        }
        return userConverter.convertToDTO(userOptional.get());
    }

}
