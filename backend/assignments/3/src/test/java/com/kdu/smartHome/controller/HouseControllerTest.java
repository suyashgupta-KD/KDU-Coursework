package com.kdu.smartHome.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdu.smartHome.entity.House;
import com.kdu.smartHome.entity.HouseMember;
import com.kdu.smartHome.entity.User;
import com.kdu.smartHome.model.HouseRole;
import com.kdu.smartHome.repository.HouseMemberRepository;
import com.kdu.smartHome.repository.HouseRepository;
import com.kdu.smartHome.repository.UserRepository;
import com.kdu.smartHome.service.CurrentUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class HouseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HouseRepository houseRepository;
    @Autowired
    HouseMemberRepository houseMemberRepository;
    @Autowired
    UserRepository userRepository;

    @MockBean
    CurrentUserService currentUserService;

    Long userId;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setEmail("h@test.com");
        user.setPassword("pw");
        user.setName("h");
        user = userRepository.save(user);
        userId = user.getUserId();

        // two houses membership
        for (int i = 1; i <= 2; i++) {
            House house = House.builder()
                    .name("House " + i)
                    .address("Addr " + i)
                    .admin(user)
                    .version(0L)
                    .build();
            house = houseRepository.save(house);
            HouseMember member = HouseMember.builder()
                    .house(house)
                    .user(user)
                    .role(HouseRole.ADMIN)
                    .version(0L)
                    .build();
            houseMemberRepository.save(member);
        }
    }

    @Test
    @DisplayName("Positive: list houses returns memberships")
    void listHouses_success() throws Exception {
        when(currentUserService.getCurrentUserId()).thenReturn(userId);

        String json = mockMvc.perform(get("/api/v1/houses").param("page", "0").param("size", "20"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode root = objectMapper.readTree(json);
        assertThat(root.get("content").size()).isEqualTo(2);
        assertThat(root.get("totalElements").asInt()).isEqualTo(2);
    }

    @Test
    @DisplayName("Negative: user with no houses sees empty page")
    void listHouses_empty() throws Exception {
        User other = new User();
        other.setEmail("empty@test.com");
        other.setPassword("pw");
        other.setName("empty");
        other = userRepository.save(other);

        when(currentUserService.getCurrentUserId()).thenReturn(other.getUserId());

        String json = mockMvc.perform(get("/api/v1/houses").param("page", "0").param("size", "20"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode root = objectMapper.readTree(json);
        assertThat(root.get("content").size()).isZero();
        assertThat(root.get("totalElements").asInt()).isZero();
    }
}
