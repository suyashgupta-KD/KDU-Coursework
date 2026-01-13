package com.smartcity.hospital.controller;

import com.smartcity.hospital.dto.request.UserRequestDto;
import com.smartcity.hospital.dto.response.PagedUserResponseDto;
import com.smartcity.hospital.dto.response.UserResponseDto;
import com.smartcity.hospital.model.User;
import com.smartcity.hospital.repository.UserJdbcRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserJdbcRepository repository;

    public UserController(UserJdbcRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public void createUser(@RequestBody UserRequestDto dto) {
        repository.save(
                dto.getUsername(),
                dto.getTimezone(),
                dto.getTenantId());
    }

    // PAGINATED + SORTED FETCH
    @GetMapping
    public PagedUserResponseDto getUsers(
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDir) {

        int offset = page * size;

        List<User> users = repository.findUsers(
                tenantId,
                size,
                offset,
                sortDir);

        long totalCount = repository.countByTenant(tenantId);

        List<UserResponseDto> responseUsers = users.stream()
                .map(u -> new UserResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getLoggedIn(),
                        u.getTimezone(),
                        u.getTenantId()))
                .toList();

        return new PagedUserResponseDto(
                responseUsers,
                totalCount,
                page,
                size);
    }
}
