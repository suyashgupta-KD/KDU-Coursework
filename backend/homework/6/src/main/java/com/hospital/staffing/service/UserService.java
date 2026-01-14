package com.hospital.staffing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.staffing.repo.UserRepository;
import com.hospital.staffing.entity.User;
import com.hospital.staffing.model.request.UserRequest;
import com.hospital.staffing.model.response.UserResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    // 1.
    private final UserRepository repository;

    // 2.
    public UserResponse create(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setRole(request.getRole());

        User saved = repository.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setRole(saved.getRole());
        return response;
    }
}
