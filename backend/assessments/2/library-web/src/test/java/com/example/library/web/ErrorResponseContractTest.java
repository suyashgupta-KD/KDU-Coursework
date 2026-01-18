package com.example.library.web;

import com.example.library.domain.entity.User;
import com.example.library.domain.enums.Role;
import com.example.library.domain.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ErrorResponseContractTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    User librarian = new User();
    librarian.setUsername("librarian");
    librarian.setPassword(passwordEncoder.encode("password"));
    librarian.setRole(Role.LIBRARIAN);
    librarian.setEnabled(true);
    userRepository.save(librarian);
  }

  @Test
  void validationErrorReturnsStandardModel() throws Exception {
    mockMvc.perform(post("/books")
            .with(httpBasic("librarian", "password"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode", equalTo("VALIDATION_ERROR")))
        .andExpect(jsonPath("$.details", hasSize(2)))
        .andExpect(jsonPath("$.path", equalTo("/books")));
  }
}
