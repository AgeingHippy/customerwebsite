package com.ageinghippy.customerwebsite.security;

import com.ageinghippy.customerwebsite.model.User;
import com.ageinghippy.customerwebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '"+username+ "' not found");
        }
        return user;
    }
}
