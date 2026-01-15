package com.softtek.demo_spring_webflux.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("attendance_records")
public class AttendanceRecord {

    @Id
    private Long id;
    private Long employeeId;
    private LocalDateTime timestamp;
    private String type; // ENTRANCE or EXIT
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;

    public AttendanceRecord() {
    }

    public AttendanceRecord(Long id, Long employeeId, LocalDateTime timestamp,
                           String type, Double latitude, Double longitude,
                           LocalDateTime createdAt) {
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

