package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto;

import java.time.LocalDate;
import java.util.List;

public class AttendanceReportResponse {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private Integer attendedDays;
    private Integer absences;
    private Integer tardies;
    private List<DailyRecordDto> records;


    public AttendanceReportResponse(){

    }
    public AttendanceReportResponse(Long employeeId, LocalDate startDate, LocalDate endDate,
                                    Integer totalDays, Integer attendedDays, Integer absences,
                                    Integer tardies, List<DailyRecordDto> records) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.attendedDays = attendedDays;
        this.absences = absences;
        this.tardies = tardies;
        this.records = records;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getAttendedDays() {
        return attendedDays;
    }

    public void setAttendedDays(Integer attendedDays) {
        this.attendedDays = attendedDays;
    }

    public Integer getAbsences() {
        return absences;
    }

    public void setAbsences(Integer absences) {
        this.absences = absences;
    }

    public Integer getTardies() {
        return tardies;
    }

    public void setTardies(Integer tardies) {
        this.tardies = tardies;
    }

    public List<DailyRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<DailyRecordDto> records) {
        this.records = records;
    }

    public static class DailyRecordDto {

        private LocalDate date;
        private String entranceTime;  // HH:mm:ss
        private String status;  // "ON_TIME", "LATE", "ABSENT"

        public DailyRecordDto() {
        }

        public DailyRecordDto(LocalDate date, String entranceTime, String status) {
            this.date = date;
            this.entranceTime = entranceTime;
            this.status = status;
        }

        // Getters y Setters

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getEntranceTime() {
            return entranceTime;
        }

        public void setEntranceTime(String entranceTime) {
            this.entranceTime = entranceTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
