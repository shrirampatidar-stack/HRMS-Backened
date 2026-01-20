package com.hrms.service;

import com.hrms.dto.EmployeeDTO;
import com.hrms.exception.ResourceNotFoundException;
import com.hrms.model.Attendance;
import com.hrms.model.Employee;
import com.hrms.repository.AttendanceRepository;
import com.hrms.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }
    
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Check if employeeId already exists
        if (employeeRepository.existsByEmployeeId(employeeDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Employee ID already exists: " + employeeDTO.getEmployeeId());
        }
        
        // Check if email already exists
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + employeeDTO.getEmail());
        }
        
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }
    
    public void deleteEmployee(Long id) {
        logger.info("Attempting to delete employee with id: {}", id);
        
        // Check if employee exists first
        if (!employeeRepository.existsById(id)) {
            logger.warn("Employee with id {} does not exist. It may have already been deleted.", id);
            // Return silently - idempotent delete (no error if already deleted)
            return;
        }
        
        // Get employee for logging and attendance deletion
        Employee employee = employeeRepository.findById(id)
                .orElse(null); // Should not be null due to existsById check above
        
        if (employee != null) {
            logger.info("Found employee: {} (ID: {})", employee.getFullName(), employee.getId());
            
            // Delete all attendance records for this employee first
            List<Attendance> attendances = attendanceRepository.findByEmployee(employee);
            logger.info("Found {} attendance records to delete", attendances.size());
            
            if (!attendances.isEmpty()) {
                attendanceRepository.deleteAll(attendances);
                logger.info("Deleted {} attendance records", attendances.size());
            }
        }
        
        // Then delete the employee using deleteById for better reliability
        employeeRepository.deleteById(id);
        logger.info("Successfully deleted employee with id: {}", id);
    }
    
    // Helper methods
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        return dto;
    }
    
    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        return employee;
    }
    
    public Employee getEmployeeEntityByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with employeeId: " + employeeId));
    }
    
    public boolean employeeExists(Long id) {
        return employeeRepository.existsById(id);
    }
}
