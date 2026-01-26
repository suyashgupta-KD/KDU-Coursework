package com.kdu.smartHome.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class DeviceControllerTest {

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

    private Long houseId;
    private Long deviceId;
    private Long roomAId;
    private Long roomBId;
    private Long memberUserId;
    private Long outsiderUserId;

    @BeforeEach
    void setUp() {
        // users
        User member = saveUser("member@test.com");
        User outsider = saveUser("out@test.com");
        memberUserId = member.getUserId();
        outsiderUserId = outsider.getUserId();

        // house + admin membership
        House house = House.builder()
                .name("Test House")
                .address("Addr")
                .admin(member)
                .version(0L)
                .build();
        house = houseRepository.save(house);
        houseId = house.getPlotId();

        HouseMember admin = HouseMember.builder()
                .house(house)
                .user(member)
                .role(HouseRole.ADMIN)
                .version(0L)
                .build();
        houseMemberRepository.save(admin);

        // rooms
        Room roomA = roomRepository.save(Room.builder().house(house).name("A").version(0L).build());
        Room roomB = roomRepository.save(Room.builder().house(house).name("B").version(0L).build());
        roomAId = roomA.getRoomId();
        roomBId = roomB.getRoomId();

        // inventory + device
        DeviceInventory inv = DeviceInventory.builder()
                .kickstonId("ABC123")
                .deviceUsername("u")
                .devicePassword("p")
                .manufactureDateTime(Instant.now())
                .manufactureFactoryPlace("X")
                .build();
        inventoryRepository.save(inv);

        Device device = Device.builder()
                .inventory(inv)
                .house(house)
                .room(roomA)
                .version(0L)
                .build();
        deviceId = deviceRepository.save(device).getDeviceId();
    }

    private User saveUser(String email) {
        User u = new User();
        u.setEmail(email);
        u.setPassword("pw");
        u.setName("n");
        return userRepository.save(u);
    }

    @Test
    @DisplayName("Positive: member moves device within house")
    void moveDevice_success() throws Exception {
        when(currentUserService.getCurrentUserId()).thenReturn(memberUserId);

        mockMvc.perform(patch("/api/v1/houses/{plotId}/devices/{deviceId}/move", houseId, deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"targetRoomId": %d}
                                """.formatted(roomBId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId", is(roomBId.intValue())));
    }

    @Test
    @DisplayName("Negative: non-member cannot move device")
    void moveDevice_forbidden() throws Exception {
        when(currentUserService.getCurrentUserId()).thenReturn(outsiderUserId);

        mockMvc.perform(patch("/api/v1/houses/{plotId}/devices/{deviceId}/move", houseId, deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"targetRoomId": %d}
                                """.formatted(roomBId)))
                .andExpect(status().isForbidden());
    }
}
