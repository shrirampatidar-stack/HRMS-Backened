package com.hrms.service;

import com.hrms.dto.AttendanceDTO;
import com.hrms.model.Attendance;
import com.hrms.model.Employee;
import com.hrms.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private EmployeeService employeeService;
    
    public AttendanceDTO markAttendance(AttendanceDTO attendanceDTO) {
        // Get employee by employeeId
        Employee employee = employeeService.getEmployeeEntityByEmployeeId(attendanceDTO.getEmployeeId());
        
        // Check if attendance already exists for this employee and date
        Attendance existingAttendance = attendanceRepository
                .findByEmployeeAndDate(employee, attendanceDTO.getDate())
                .orElse(null);
        
        if (existingAttendance != null) {
            // Update existing attendance
            existingAttendance.setStatus(attendanceDTO.getStatus());
            Attendance updated = attendanceRepository.save(existingAttendance);
            return convertToDTO(updated);
        } else {
            // Create new attendance
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setDate(attendanceDTO.getDate());
            attendance.setStatus(attendanceDTO.getStatus());
            Attendance saved = attendanceRepository.save(attendance);
            return convertToDTO(saved);
        }
    }
    
    public List<AttendanceDTO> getAttendanceByEmployeeId(String employeeId) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);
        return attendances.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByDate(date);
        return attendances.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getRecentAttendance(int limit) {
        List<Attendance> attendances = attendanceRepository.findAllOrderByDateDesc();
        return attendances.stream()
                .limit(limit)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public long countPresentByDate(LocalDate date) {
        return attendanceRepository.countPresentByDate(date);
    }
    
    public long countAbsentByDate(LocalDate date) {
        return attendanceRepository.countAbsentByDate(date);
    }
    
    // Helper methods
    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setEmployeeId(attendance.getEmployee().getEmployeeId());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus());
        dto.setEmployeeName(attendance.getEmployee().getFullName());
        dto.setEmail(attendance.getEmployee().getEmail());
        dto.setDepartment(attendance.getEmployee().getDepartment());
        return dto;
    }
}
