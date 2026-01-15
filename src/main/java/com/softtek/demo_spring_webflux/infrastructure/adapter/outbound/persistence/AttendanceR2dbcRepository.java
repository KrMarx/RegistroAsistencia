package com.softtek.demo_spring_webflux.infrastructure.adapter.outbound.persistence;

import com.softtek.demo_spring_webflux.domain.model.AttendanceRecord;
import org.springframework.cglib.core.Local;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface AttendanceR2dbcRepository extends R2dbcRepository<AttendanceRecord, Long> {
    Flux<AttendanceRecord> findByEmployeeId(Long employeeId);
    Flux<AttendanceRecord> findByEmployeeIdAndType(Long employeeId, String type);
    @Query("SELECT * FROM attendance_records WHERE employee_id = :employeeId and type = 'ENTRANCE' " +
            "AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC " )
    Flux<AttendanceRecord> findByEmployeeIdAndDateRange(
            long employeeId, LocalDateTime startDate, LocalDateTime endDate
    );
}

