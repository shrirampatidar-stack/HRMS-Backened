package com.hrms.dto;

import java.util.List;

public class DashboardStatsDTO {
    
    private long totalEmployees;
    private long presentToday;
    private long absentToday;
    private List<AttendanceDTO> recentAttendance;
    
    // Constructors
    public DashboardStatsDTO() {
    }
    
    public DashboardStatsDTO(long totalEmployees, long presentToday, long absentToday, List<AttendanceDTO> recentAttendance) {
        this.totalEmployees = totalEmployees;
        this.presentToday = presentToday;
        this.absentToday = absentToday;
        this.recentAttendance = recentAttendance;
    }
    
    // Getters and Setters
    public long getTotalEmployees() {
        return totalEmployees;
    }
    
    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }
    
    public long getPresentToday() {
        return presentToday;
    }
    
    public void setPresentToday(long presentToday) {
        this.presentToday = presentToday;
    }
    
    public long getAbsentToday() {
        return absentToday;
    }
    
    public void setAbsentToday(long absentToday) {
        this.absentToday = absentToday;
    }
    
    public List<AttendanceDTO> getRecentAttendance() {
        return recentAttendance;
    }
    
    public void setRecentAttendance(List<AttendanceDTO> recentAttendance) {
        this.recentAttendance = recentAttendance;
    }
}
