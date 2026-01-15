package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class AttendanceResponse {

    @Schema(description = "Unique identifier of the attendance record", example = "1")
    private Long id;

    @Schema(description = "Employee ID", example = "1")
    private Long employeeId;

    @Schema(description = "Timestamp of the record", example = "2024-01-01T08:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Type of attendance", example = "ENTRANCE", allowableValues = {"ENTRANCE", "EXIT"})
    private String type;

    @Schema(description = "Latitude coordinate", example = "40.7128")
    private Double latitude;

    @Schema(description = "Longitude coordinate", example = "-74.0060")
    private Double longitude;

    @Schema(description = "Creation timestamp", example = "2024-01-01T08:00:00")
    private LocalDateTime createdAt;

    public AttendanceResponse() {
    }

    public AttendanceResponse(Long id, Long employeeId, LocalDateTime timestamp, String type,
                             Double latitude, Double longitude, LocalDateTime createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.timestamp = timestamp;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

