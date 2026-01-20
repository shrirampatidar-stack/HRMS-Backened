package com.hrms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {
    
    private Long id;
    
    @NotBlank(message = "Employee ID is required")
    @Size(max = 50, message = "Employee ID must not exceed 50 characters")
    private String employeeId;
    
    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department must not exceed 50 characters")
    private String department;
    
    // Constructors
    public EmployeeDTO() {
    }
    
    public EmployeeDTO(String employeeId, String fullName, String email, String department) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
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
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
