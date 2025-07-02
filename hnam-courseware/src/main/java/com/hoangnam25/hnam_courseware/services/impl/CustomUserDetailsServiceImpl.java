package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.model.entity.CustomUserDetails;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new CustomUserDetails(user);
    }
}
