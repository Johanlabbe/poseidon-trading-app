package com.poseidon.config;

import com.poseidon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration using session-based authentication.
 * Provides formLogin, logout and role-based authorization.
 * <p>
 * Javadoc is required for all security-related classes and methods.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final UserRepository userRepository;

        /**
         * Password encoder bean using BCrypt.
         * 
         * @return the PasswordEncoder implementation
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        /**
         * UserDetailsService that loads users from the database.
         * 
         * @return a UserDetailsService used by Spring Security
         */
        @Bean
        public UserDetailsService userDetailsService() {
                return username -> userRepository.findByUsername(username)
                                .map(u -> org.springframework.security.core.userdetails.User
                                                .withUsername(u.getUsername())
                                                .password(u.getPassword())
                                                .roles(u.getRole().replace("ROLE_", ""))
                                                .build())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        }

        /**
         * Main security filter chain configuring endpoints protection.
         * CSRF is kept enabled to protect form-based UI.
         * 
         * @param http HttpSecurity
         * @return SecurityFilterChain
         * @throws Exception if the configuration fails
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/login", "/api/health"))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/css/**", "/login", "/error", "/h2-console/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .defaultSuccessUrl("/home", true)
                                                .permitAll())
                                .logout(l -> l.invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID"));

                http.headers(h -> h.frameOptions(f -> f.sameOrigin()));

                return http.build();
        }
}
