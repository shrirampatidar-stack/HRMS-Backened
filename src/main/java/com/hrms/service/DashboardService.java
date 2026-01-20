package com.hrms.service;

import com.hrms.dto.AttendanceDTO;
import com.hrms.dto.DashboardStatsDTO;
import com.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private AttendanceService attendanceService;
    
    public DashboardStatsDTO getDashboardStats() {
        long totalEmployees = employeeRepository.count();
        LocalDate today = LocalDate.now();
        long presentToday = attendanceService.countPresentByDate(today);
        long absentToday = attendanceService.countAbsentByDate(today);
        List<AttendanceDTO> recentAttendance = attendanceService.getRecentAttendance(10);
        
        return new DashboardStatsDTO(totalEmployees, presentToday, absentToday, recentAttendance);
    }
}
