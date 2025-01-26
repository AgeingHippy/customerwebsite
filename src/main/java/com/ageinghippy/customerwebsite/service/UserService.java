package com.ageinghippy.customerwebsite.service;

import com.ageinghippy.customerwebsite.model.User;
import com.ageinghippy.customerwebsite.repository.RoleRepository;
import com.ageinghippy.customerwebsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles().isEmpty()) {
            user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        }
        return userRepository.save(user);
    }
}
