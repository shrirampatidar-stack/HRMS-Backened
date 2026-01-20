package com.hrms.repository;

import com.hrms.model.Attendance;
import com.hrms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    List<Attendance> findByEmployee(Employee employee);
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM Attendance a WHERE a.employee = :employee")
    void deleteByEmployee(@Param("employee") Employee employee);
    
    List<Attendance> findByDate(LocalDate date);
    
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    
    @Query("SELECT a FROM Attendance a WHERE a.employee.employeeId = :employeeId")
    List<Attendance> findByEmployeeId(@Param("employeeId") String employeeId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :date AND a.status = 'Present'")
    long countPresentByDate(@Param("date") LocalDate date);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :date AND a.status = 'Absent'")
    long countAbsentByDate(@Param("date") LocalDate date);
    
    @Query("SELECT a FROM Attendance a ORDER BY a.date DESC, a.id DESC")
    List<Attendance> findAllOrderByDateDesc();
}
