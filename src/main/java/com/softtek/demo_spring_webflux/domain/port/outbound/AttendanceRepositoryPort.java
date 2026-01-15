package com.softtek.demo_spring_webflux.domain.port.outbound;

import com.softtek.demo_spring_webflux.domain.model.AttendanceRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AttendanceRepositoryPort {
    Mono<AttendanceRecord> save(AttendanceRecord record);
    Mono<AttendanceRecord> findById(Long id);
    Flux<AttendanceRecord> findAll();
    Flux<AttendanceRecord> findByEmployeeId(Long employeeId);
    Mono<Void> deleteById(Long id);
    Flux<AttendanceRecord> findByEmployeeIdAndDateRange(
            long employeeId, LocalDateTime startDate, LocalDateTime endDate
    );
}

