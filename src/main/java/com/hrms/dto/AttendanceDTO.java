package com.hrms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AttendanceDTO {
    
    private Long id;
    
    @NotBlank(message = "Employee ID is required")
    private String employeeId;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotBlank(message = "Status is required")
    private String status;
    
    // Additional fields for response
    private String employeeName;
    private String email;
    private String department;
    
    // Constructors
    public AttendanceDTO() {
    }
    
    public AttendanceDTO(String employeeId, LocalDate date, String status) {
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
}
