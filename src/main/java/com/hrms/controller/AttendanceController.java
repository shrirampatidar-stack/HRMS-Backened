package com.hrms.controller;

import com.hrms.dto.AttendanceDTO;
import com.hrms.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    @Autowired
    private AttendanceService attendanceService;
    
    @PostMapping
    public ResponseEntity<AttendanceDTO> markAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO savedAttendance = attendanceService.markAttendance(attendanceDTO);
        return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
    }
    
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEmployeeId(@PathVariable String employeeId) {
        List<AttendanceDTO> attendances = attendanceService.getAttendanceByEmployeeId(employeeId);
        return ResponseEntity.ok(attendances);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<AttendanceDTO> attendances = attendanceService.getAttendanceByDate(localDate);
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }
}
