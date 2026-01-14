package com.hospital.staffing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.hospital.staffing.repo.UserRepository;
import com.hospital.staffing.entity.User;
import com.hospital.staffing.model.response.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    // 1.
    private final UserRepository repository;

    // 2.
    public UserPageResponse getUsers(int page, int size) {

        // 3.
        if (size < 1 || size > 50) {
            throw new IllegalArgumentException("size must be between 1 and 50");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<User> result = repository.findAll(pageable);

        // 4.
        List<UserResponse> users = result.getContent().stream().map(user -> {
            UserResponse dto = new UserResponse();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setRole(user.getRole());
            return dto;
        }).toList();

        // 5.
        UserPageResponse response = new UserPageResponse();
        response.setUsers(users);
        response.setPage(result.getNumber());
        response.setSize(result.getSize());
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());

        return response;
    }
}
