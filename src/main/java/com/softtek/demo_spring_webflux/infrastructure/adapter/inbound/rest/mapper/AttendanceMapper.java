package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.mapper;

import com.softtek.demo_spring_webflux.domain.model.AttendanceRecord;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceResponse;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public AttendanceResponse toResponse(AttendanceRecord record) {
        return new AttendanceResponse(
                record.getId(),
                record.getEmployeeId(),
                record.getTimestamp(),
                record.getType(),
                record.getLatitude(),
                record.getLongitude(),
                record.getCreatedAt()
        );
    }
}

