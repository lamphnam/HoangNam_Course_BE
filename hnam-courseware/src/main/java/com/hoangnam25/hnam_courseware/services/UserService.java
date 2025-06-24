package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.converter.UserConverter;
import com.hoangnam25.hnam_courseware.model.dtos.UserRequest;
import com.hoangnam25.hnam_courseware.model.dtos.UserResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return userConverter.convertToDTO(userOptional.get());
    }

    public UserResponseDto updateUserInfo(String username, UserRequest request) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setImageUrl(request.getImageUrl());

        userRepository.save(user);

        return userConverter.convertToDTO(user);
    }

}
