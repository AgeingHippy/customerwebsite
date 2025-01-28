package com.ageinghippy.customerwebsite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/webjars/**", "/css/**", "/images/**", "js/**").permitAll()
                        .requestMatchers("/" ,"/index","/homepage","/error-page").permitAll()
                        .requestMatchers("/login", "/register").anonymous()
                        .requestMatchers("/customer/view").hasRole("USER")
                        .anyRequest().hasRole("ADMIN")
                )
//                .formLogin(Customizer.withDefaults());
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/homepage",true));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
