package com.eventsphere.config;

import com.eventsphere.entity.User;
import com.eventsphere.model.Role;
import com.eventsphere.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultUserConfig {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserConfig.class);

    @Bean
    CommandLineRunner ensureDefaultUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createIfMissing(userRepository, passwordEncoder, "admin", "admin123", Role.ADMIN);
            createIfMissing(userRepository, passwordEncoder, "user1", "user1pass", Role.USER);
            createIfMissing(userRepository, passwordEncoder, "user2", "user2pass", Role.USER);
        };
    }

    private void createIfMissing(UserRepository repo, PasswordEncoder encoder, String username, String rawPassword, Role role) {
        if (repo.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode(rawPassword));
            user.setRole(role);
            user.setEnabled(true);
            repo.save(user);
            log.info("Default account available: {} / {}", username, rawPassword);
        }
    }
}
