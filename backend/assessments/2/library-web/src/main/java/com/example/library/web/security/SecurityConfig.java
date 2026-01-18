package com.example.library.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers(HttpMethod.POST, "/books").hasRole("LIBRARIAN")
            .requestMatchers(HttpMethod.PATCH, "/books/*/catalog").hasRole("LIBRARIAN")
            .requestMatchers(HttpMethod.GET, "/books").hasAnyRole("LIBRARIAN", "MEMBER")
            .requestMatchers(HttpMethod.POST, "/loans/*/borrow").hasRole("MEMBER")
            .requestMatchers(HttpMethod.POST, "/loans/*/return").hasRole("MEMBER")
            .requestMatchers(HttpMethod.GET, "/analytics/audit").hasRole("LIBRARIAN")
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
