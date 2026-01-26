package com.kdu.smartHome.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdu.smartHome.entity.*;
import com.kdu.smartHome.model.HouseRole;
import com.kdu.smartHome.repository.*;
import com.kdu.smartHome.service.CurrentUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class RoomsWithDevicesIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    HouseMemberRepository houseMemberRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DeviceInventoryRepository inventoryRepository;

    @MockBean
    CurrentUserService currentUserService;

    Long plotId;
    Long userId;
    Long room1Id;
    Long room2Id;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setEmail("rooms@test.com");
        user.setPassword("pw");
        user.setName("rooms");
        user = userRepository.save(user);
        userId = user.getUserId();

        House house = House.builder()
                .name("HouseR")
                .address("A")
                .admin(user)
                .version(0L)
                .build();
        house = houseRepository.save(house);
        plotId = house.getPlotId();

        HouseMember member = HouseMember.builder()
                .house(house)
                .user(user)
                .role(HouseRole.ADMIN)
                .version(0L)
                .build();
        houseMemberRepository.save(member);

        Room r1 = roomRepository.save(Room.builder().house(house).name("Kitchen").version(0L).build());
        Room r2 = roomRepository.save(Room.builder().house(house).name("Hall").version(0L).build());
        room1Id = r1.getRoomId();
        room2Id = r2.getRoomId();

        DeviceInventory inv1 = inventoryRepository.save(DeviceInventory.builder()
                .kickstonId("DEV001")
                .deviceUsername("u1")
                .devicePassword("p1")
                .manufactureDateTime(Instant.now())
                .manufactureFactoryPlace("F")
                .build());
        DeviceInventory inv2 = inventoryRepository.save(DeviceInventory.builder()
                .kickstonId("DEV002")
                .deviceUsername("u2")
                .devicePassword("p2")
                .manufactureDateTime(Instant.now())
                .manufactureFactoryPlace("F")
                .build());

        deviceRepository.save(Device.builder()
                .inventory(inv1)
                .house(house)
                .room(r1)
                .version(0L)
                .build());

        deviceRepository.save(Device.builder()
                .inventory(inv2)
                .house(house)
                .version(0L)
                .build()); // unassigned
    }

    @Test
    @DisplayName("Rooms with devices returns assigned and unassigned")
    void roomsWithDevices_success() throws Exception {
        when(currentUserService.getCurrentUserId()).thenReturn(userId);

        String json = mockMvc.perform(get("/api/v1/houses/{plotId}/rooms-with-devices", plotId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode root = objectMapper.readTree(json);
        assertThat(root.get("rooms").size()).isEqualTo(2);
        assertThat(root.get("unassignedDevices").size()).isEqualTo(1);
    }
}
