package com.ageinghippy.customerwebsite.service;

import com.ageinghippy.customerwebsite.model.Role;
import com.ageinghippy.customerwebsite.model.User;
import com.ageinghippy.customerwebsite.repository.RoleRepository;
import com.ageinghippy.customerwebsite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
