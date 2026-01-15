package com.softtek.demo_spring_webflux.infrastructure.adapter.outbound.persistence;

import com.softtek.demo_spring_webflux.domain.model.AttendanceRecord;
import com.softtek.demo_spring_webflux.domain.port.outbound.AttendanceRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class AttendanceRepositoryAdapter implements AttendanceRepositoryPort {

    private final AttendanceR2dbcRepository attendanceR2dbcRepository;

    public AttendanceRepositoryAdapter(AttendanceR2dbcRepository attendanceR2dbcRepository) {
        this.attendanceR2dbcRepository = attendanceR2dbcRepository;
    }

    @Override
    public Mono<AttendanceRecord> save(AttendanceRecord record) {
        return attendanceR2dbcRepository.save(record);
    }

    @Override
    public Mono<AttendanceRecord> findById(Long id) {
        return attendanceR2dbcRepository.findById(id);
    }

    @Override
    public Flux<AttendanceRecord> findAll() {
        return attendanceR2dbcRepository.findAll();
    }

    @Override
    public Flux<AttendanceRecord> findByEmployeeId(Long employeeId) {
        return attendanceR2dbcRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return attendanceR2dbcRepository.deleteById(id);
    }
    @Override
    public Flux<AttendanceRecord> findByEmployeeIdAndDateRange(
            long employeeId, LocalDateTime startDate, LocalDateTime endDate
    ){
        return  attendanceR2dbcRepository.findByEmployeeIdAndDateRange(employeeId, startDate, endDate);
    }
}

