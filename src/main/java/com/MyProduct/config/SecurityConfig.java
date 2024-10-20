package com.MyProduct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)  // Enable method-level security (e.g., @PreAuthorize)
public class SecurityConfig {

    // Configure the SecurityFilterChain with Basic Authentication and Role-based access control
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/customers/**").hasRole("ADMIN")  // Protect this path for ADMIN role
                        .anyRequest().authenticated()  // All other requests must be authenticated
                )
                .httpBasic(httpBasic -> httpBasic.realmName("Customer Data API"));  // Enable Basic Authentication

        return http.build();
    }

    // Define an in-memory user with ADMIN role
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin_password"))  // Encode the password using BCrypt
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);  // Use in-memory authentication
    }

    // Define a password encoder (BCryptPasswordEncoder is a good practice)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt is recommended for password hashing
    }
}
