package com.softtek.demo_spring_webflux.application.service;

import com.softtek.demo_spring_webflux.domain.model.AttendanceRecord;
import com.softtek.demo_spring_webflux.domain.port.inbound.AttendanceServicePort;
import com.softtek.demo_spring_webflux.domain.port.outbound.AttendanceRepositoryPort;
import com.softtek.demo_spring_webflux.domain.port.outbound.EmployeeRepositoryPort;

import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceReportResponse;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceReportResponse.DailyRecordDto;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService implements AttendanceServicePort {

    private final AttendanceRepositoryPort attendanceRepositoryPort;
    private final EmployeeRepositoryPort employeeRepositoryPort;
    //@01
    private static final LocalTime WORK_START_TIME = LocalTime.of(9, 0);  // 09:00 AM

    public AttendanceService(AttendanceRepositoryPort attendanceRepositoryPort,
                            EmployeeRepositoryPort employeeRepositoryPort) {
        this.attendanceRepositoryPort = attendanceRepositoryPort;
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public Mono<AttendanceRecord> registerEntrance(Long employeeId, Double latitude, Double longitude) {
        return employeeRepositoryPort.findById(employeeId)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found with id: " + employeeId)))
                .flatMap(employee -> {
                    AttendanceRecord record = new AttendanceRecord();
                    record.setEmployeeId(employeeId);
                    record.setTimestamp(LocalDateTime.now());
                    record.setType("ENTRANCE");
                    record.setLatitude(latitude);
                    record.setLongitude(longitude);
                    record.setCreatedAt(LocalDateTime.now());
                    return attendanceRepositoryPort.save(record);
                });
    }

    @Override
    public Mono<AttendanceRecord> registerExit(Long employeeId, Double latitude, Double longitude) {
        return employeeRepositoryPort.findById(employeeId)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found with id: " + employeeId)))
                .flatMap(employee -> {
                    AttendanceRecord record = new AttendanceRecord();
                    record.setEmployeeId(employeeId);
                    record.setTimestamp(LocalDateTime.now());
                    record.setType("EXIT");
                    record.setLatitude(latitude);
                    record.setLongitude(longitude);
                    record.setCreatedAt(LocalDateTime.now());
                    return attendanceRepositoryPort.save(record);
                });
    }

    @Override
    public Mono<AttendanceRecord> getRecordById(Long id) {
        return attendanceRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Attendance record not found with id: " + id)));
    }

    @Override
    public Flux<AttendanceRecord> getAllRecords() {
        return attendanceRepositoryPort.findAll();
    }

    @Override
    public Flux<AttendanceRecord> getRecordsByEmployeeId(Long employeeId) {
        return attendanceRepositoryPort.findByEmployeeId(employeeId);
    }

    @Override
    public Mono<Void> deleteRecord(Long id) {
        return attendanceRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Attendance record not found with id: " + id)))
                .flatMap(record -> attendanceRepositoryPort.deleteById(id));
    }

    @Override
    public Flux<AttendanceRecord> getRecordsByEmployeeIdAndDateRange(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate) {

        // Convertir LocalDate a LocalDateTime (inicio y fin del día)
        LocalDateTime startDateTime = startDate.atStartOfDay();        // 2025-01-01 00:00:00
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);     // 2025-01-31 23:59:59.999999999

        // Llamar al repository con el rango de fechas completo
        return attendanceRepositoryPort.findByEmployeeIdAndDateRange(
                employeeId,
                startDateTime,
                endDateTime
        );
    }


    // ========================================
    // NUEVO MÉTODO: Generar reporte de asistencia
    // ========================================
    @Override
    public Mono<AttendanceReportResponse> getAttendanceReport(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate) {

        // PASO 1: Convertir fechas a DateTime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // PASO 2: Obtener registros del rango de fechas
        return attendanceRepositoryPort.findByEmployeeIdAndDateRange(
                        employeeId,
                        startDateTime,
                        endDateTime
                )
                // PASO 3: Convertir Flux<AttendanceRecord> a List<AttendanceRecord>
                .collectList()
                // PASO 4: Procesar los registros y generar el reporte
                .map(records -> {
                    // Calcular días totales en el rango
                    long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
                    // Agrupar registros por fecha
                    Map<LocalDate, AttendanceRecord> recordsByDate = records.stream()
                            .collect(Collectors.toMap(
                                    record -> record.getTimestamp().toLocalDate(),
                                    record -> record,
                                    (existing, replacement) -> existing  // Si hay duplicados, mantener el primero
                            ));

                    // Calcular estadísticas
                    int attendedDays = recordsByDate.size();
                    int absences = (int) (totalDays - attendedDays);
                    int tardies = 0;

                    // Construir lista de registros diarios
                    List<DailyRecordDto> dailyRecords = new ArrayList<>();

                    // Iterar por cada día del rango
                    LocalDate currentDate = startDate;
                    while (!currentDate.isAfter(endDate)) {

                        AttendanceRecord record = recordsByDate.get(currentDate);

                        if (record != null) {
                            // Hay registro para este día
                            LocalTime entranceTime = record.getTimestamp().toLocalTime();
                            String status;

                            // Verificar si llegó tarde
                            if (entranceTime.isAfter(WORK_START_TIME)) {
                                status = "TARDE";
                                tardies++;
                            } else {
                                status = "A TIEMPO";
                            }

                            dailyRecords.add(new DailyRecordDto(
                                    currentDate,
                                    entranceTime.toString(),  // "09:15:30"
                                    status
                            ));

                        } else {
                            // No hay registro = falta
                            dailyRecords.add(new DailyRecordDto(
                                    currentDate,
                                    null,  // Sin hora de entrada
                                    "AUSENTE"
                            ));
                        }

                        currentDate = currentDate.plusDays(1);
                    }

                    // PASO 5: Construir el objeto de respuesta
                    return new AttendanceReportResponse(
                            employeeId,
                            startDate,
                            endDate,
                            (int) totalDays,
                            attendedDays,
                            absences,
                            tardies,
                            dailyRecords
                    );
                });
    }
}

