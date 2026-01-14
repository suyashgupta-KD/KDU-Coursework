package com.hospital.staffing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.staffing.repo.*;
import com.hospital.staffing.entity.ShiftUser;
import com.hospital.staffing.model.request.ShiftUserRequest;
import com.hospital.staffing.model.response.ShiftUserResponse;

@Service
@RequiredArgsConstructor
public class ShiftUserService {

    // 1.
    private final ShiftUserRepository repository;
    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;

    // 2.
    public ShiftUserResponse create(ShiftUserRequest request) {
        ShiftUser su = new ShiftUser();
        su.setUser(userRepository.findById(request.getUserId()).orElseThrow());
        su.setShift(shiftRepository.findById(request.getShiftId()).orElseThrow());

        ShiftUser saved = repository.save(su);

        ShiftUserResponse response = new ShiftUserResponse();
        response.setId(saved.getId());
        response.setUserId(saved.getUser().getId());
        response.setShiftId(saved.getShift().getId());
        return response;
    }
}
