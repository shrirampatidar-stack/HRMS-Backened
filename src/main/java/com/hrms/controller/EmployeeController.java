package com.hrms.controller;

import com.hrms.dto.EmployeeDTO;
import com.hrms.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) {
        logger.info("DELETE request received for employee id: {}", id);
        try {
            // Check if employee exists before attempting delete
            boolean exists = employeeService.employeeExists(id);
            if (!exists) {
                logger.warn("Employee with id {} does not exist", id);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Employee not found or already deleted");
                response.put("success", "true"); // Still return success for idempotent operation
                return ResponseEntity.ok(response);
            }
            
            employeeService.deleteEmployee(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee deleted successfully");
            response.put("success", "true");
            logger.info("Employee {} deleted successfully", id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleting employee {}: {}", id, e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete employee: " + e.getMessage());
            errorResponse.put("success", "false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
