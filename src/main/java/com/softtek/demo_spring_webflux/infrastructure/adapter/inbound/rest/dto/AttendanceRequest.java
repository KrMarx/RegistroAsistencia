package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class AttendanceRequest {

    @Schema(description = "Employee ID", example = "1", required = true)
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @Schema(description = "Latitude coordinate", example = "40.7128", required = true)
    @NotNull(message = "Latitude is required")
    private Double latitude;

    @Schema(description = "Longitude coordinate", example = "-74.0060", required = true)
    @NotNull(message = "Longitude is required")
    private Double longitude;

    public AttendanceRequest() {
    }

    public AttendanceRequest(Long employeeId, Double latitude, Double longitude) {
        this.employeeId = employeeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

