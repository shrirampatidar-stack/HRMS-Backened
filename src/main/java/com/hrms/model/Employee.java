package com.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees", uniqueConstraints = {
    @UniqueConstraint(columnNames = "employeeId"),
    @UniqueConstraint(columnNames = "email")
})
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Employee ID is required")
    @Size(max = 50, message = "Employee ID must not exceed 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    private String employeeId;
    
    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String fullName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department must not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String department;
    
    // Constructors
    public Employee() {
    }
    
    public Employee(String employeeId, String fullName, String email, String department) {
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
