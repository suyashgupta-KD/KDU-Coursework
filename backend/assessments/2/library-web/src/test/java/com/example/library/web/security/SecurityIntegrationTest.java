package com.example.library.web.security;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    createUser("librarian", Role.LIBRARIAN, "password");
    createUser("member", Role.MEMBER, "password");
  }

  @Test
  void memberCannotCreateBook() throws Exception {
    mockMvc.perform(post("/books")
            .with(httpBasic("member", "password"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"A Book\"}"))
        .andExpect(status().isForbidden());
  }

  @Test
  void unauthenticatedRequestGets401() throws Exception {
    mockMvc.perform(get("/books"))
        .andExpect(status().isUnauthorized());
  }

  private void createUser(String username, Role role, String rawPassword) {
    User user = new User();
    user.setUsername(username);
    user.setRole(role);
    user.setEnabled(true);
    user.setPassword(passwordEncoder.encode(rawPassword));
    userRepository.save(user);
  }
}
