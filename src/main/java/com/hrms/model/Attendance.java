package com.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "attendance", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"employee_id", "date"})
})
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Employee is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;
    
    @NotBlank(message = "Status is required")
    @Column(nullable = false, length = 20)
    private String status; // "Present", "Absent", etc.
    
    // Constructors
    public Attendance() {
    }
    
    public Attendance(Employee employee, LocalDate date, String status) {
        this.employee = employee;
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
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
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
}
