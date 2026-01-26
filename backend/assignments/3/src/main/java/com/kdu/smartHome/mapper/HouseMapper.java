package com.kdu.smartHome.mapper;

import com.kdu.smartHome.dto.request.CreateHouseRequest;
import com.kdu.smartHome.dto.response.HouseResponse;
import com.kdu.smartHome.entity.House;

/**
 * Maps house entities to DTOs.
 */

public final class HouseMapper {
    private HouseMapper() {
    }

    public static HouseResponse toDto(House house) {
        return HouseResponse.builder()
                .plotId(house.getPlotId())
                .name(house.getName())
                .address(house.getAddress())
                .build();
    }

    public static House toEntity(CreateHouseRequest request) {
        return House.builder()
                .name(request.getName())
                .address(request.getAddress())
                .build();
    }
}
