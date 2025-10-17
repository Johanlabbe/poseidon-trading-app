package com.poseidon.config;

import com.poseidon.domain.User;
import com.poseidon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Development data seeding: creates default admin and user accounts.
 */
@Configuration
@RequiredArgsConstructor
public class DevDataLoader {

    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .username("admin")
                        .password(encoder.encode("Admin#2025!"))
                        .fullname("Admin")
                        .role("ROLE_ADMIN")
                        .build());
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                userRepository.save(User.builder()
                        .username("user")
                        .password(encoder.encode("User#2025!"))
                        .fullname("Standard User")
                        .role("ROLE_USER")
                        .build());
            }
        };
    }
}
