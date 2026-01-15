package com.softtek.demo_spring_webflux.domain.port.inbound;

import com.softtek.demo_spring_webflux.domain.model.AttendanceRecord;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceReportResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface AttendanceServicePort {
    Mono<AttendanceRecord> registerEntrance(Long employeeId, Double latitude, Double longitude);
    Mono<AttendanceRecord> registerExit(Long employeeId, Double latitude, Double longitude);
    Mono<AttendanceRecord> getRecordById(Long id);
    Flux<AttendanceRecord> getAllRecords();
    Flux<AttendanceRecord> getRecordsByEmployeeId(Long employeeId);
    Mono<Void> deleteRecord(Long id);
    Flux<AttendanceRecord> getRecordsByEmployeeIdAndDateRange(Long employeeId,LocalDate startDate,LocalDate endDate);
    Mono<AttendanceReportResponse> getAttendanceReport(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );
}

