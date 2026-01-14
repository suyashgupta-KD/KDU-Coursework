package com.hospital.staffing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.staffing.repo.ShiftTypeRepository;
import com.hospital.staffing.entity.ShiftType;
import com.hospital.staffing.model.request.ShiftTypeRequest;
import com.hospital.staffing.model.response.ShiftTypeResponse;

@Service
@RequiredArgsConstructor
public class ShiftTypeService {

    // 1.
    private final ShiftTypeRepository repository;

    // 2.
    public ShiftTypeResponse create(ShiftTypeRequest request) {
        ShiftType entity = new ShiftType();
        entity.setName(request.getName());

        ShiftType saved = repository.save(entity);

        ShiftTypeResponse response = new ShiftTypeResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        return response;
    }
}
