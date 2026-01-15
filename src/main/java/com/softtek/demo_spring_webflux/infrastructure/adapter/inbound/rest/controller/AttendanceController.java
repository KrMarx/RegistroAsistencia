package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.controller;

import com.softtek.demo_spring_webflux.domain.port.inbound.AttendanceServicePort;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceRequest;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceResponse;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.mapper.AttendanceMapper;

import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.AttendanceReportResponse;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance Management", description = "APIs for managing employee attendance (entrance/exit)")
public class AttendanceController {

    private final AttendanceServicePort attendanceServicePort;
    private final AttendanceMapper attendanceMapper;

    public AttendanceController(AttendanceServicePort attendanceServicePort, AttendanceMapper attendanceMapper) {
        this.attendanceServicePort = attendanceServicePort;
        this.attendanceMapper = attendanceMapper;
    }

    @PostMapping("/entrance")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register employee entrance", description = "Registers an employee entrance with location data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrance registered successfully",
                    content = @Content(schema = @Schema(implementation = AttendanceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    public Mono<AttendanceResponse> registerEntrance(@Valid @RequestBody AttendanceRequest request) {
        return attendanceServicePort.registerEntrance(
                        request.getEmployeeId(),
                        request.getLatitude(),
                        request.getLongitude())
                .map(attendanceMapper::toResponse);
    }

    @PostMapping("/exit")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register employee exit", description = "Registers an employee exit with location data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exit registered successfully",
                    content = @Content(schema = @Schema(implementation = AttendanceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    public Mono<AttendanceResponse> registerExit(@Valid @RequestBody AttendanceRequest request) {
        return attendanceServicePort.registerExit(
                        request.getEmployeeId(),
                        request.getLatitude(),
                        request.getLongitude())
                .map(attendanceMapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get attendance record by ID", description = "Retrieves a specific attendance record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record found",
                    content = @Content(schema = @Schema(implementation = AttendanceResponse.class))),
            @ApiResponse(responseCode = "404", description = "Record not found",
                    content = @Content)
    })
    public Mono<AttendanceResponse> getRecordById(
            @Parameter(description = "ID of the attendance record", required = true)
            @PathVariable Long id) {
        return attendanceServicePort.getRecordById(id)
                .map(attendanceMapper::toResponse);
    }

    @GetMapping
    @Operation(summary = "Get all attendance records", description = "Retrieves all attendance records in the system")
    @ApiResponse(responseCode = "200", description = "List of records retrieved successfully")
    public Flux<AttendanceResponse> getAllRecords() {
        return attendanceServicePort.getAllRecords()
                .map(attendanceMapper::toResponse);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get attendance records by employee", description = "Retrieves all attendance records for a specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of records retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    public Flux<AttendanceResponse> getRecordsByEmployeeId(
            @Parameter(description = "ID of the employee", required = true)
            @PathVariable Long employeeId) {
        return attendanceServicePort.getRecordsByEmployeeId(employeeId)
                .map(attendanceMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete attendance record", description = "Deletes a specific attendance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found",
                    content = @Content)
    })
    public Mono<Void> deleteRecord(
            @Parameter(description = "ID of the attendance record to delete", required = true)
            @PathVariable Long id) {
        return attendanceServicePort.deleteRecord(id);
    }

    @GetMapping("/employee/{employeeId}/range")
    @Operation(
            summary = "Get attendance records by employee and date range",
            description = "Retrieves all attendance records for a specific employee within a date range"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of records retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid date format or date range",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content
            )
    })

    public Flux<AttendanceResponse> getRecordsByEmployeeIdAndDateRange(
            @Parameter(description = "ID of the employee", required = true)
            @PathVariable Long employeeId,

            @Parameter(description = "Start date (format: yyyy-MM-dd)", required = true, example = "2025-01-01")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @Parameter(description = "End date (format: yyyy-MM-dd)", required = true, example = "2025-01-31")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return attendanceServicePort.getRecordsByEmployeeIdAndDateRange(
                        employeeId,
                        startDate,
                        endDate
                )
                .map(attendanceMapper::toResponse);
    }

    @GetMapping("/employee/{employeeId}/report")
    @Operation(
            summary = "Get attendance report for employee",
            description = "Generates a report of absences and tardies for a specific employee within a date range"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Report generated successfully",
                    content = @Content(schema = @Schema(implementation = AttendanceReportResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid date format or date range",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content
            )
    })
    public Mono<AttendanceReportResponse> getAttendanceReport(
            @Parameter(description = "ID of the employee", required = true)
            @PathVariable Long employeeId,

            @Parameter(description = "Start date (format: yyyy-MM-dd)", required = true, example = "2025-01-01")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @Parameter(description = "End date (format: yyyy-MM-dd)", required = true, example = "2025-01-31")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return attendanceServicePort.getAttendanceReport(employeeId, startDate, endDate);
    }

}

