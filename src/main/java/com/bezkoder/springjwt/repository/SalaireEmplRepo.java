package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SalaireEmplRepo extends JpaRepository<SalaireEmployee,Long> {
List<SalaireEmployee> findByEmployeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    List<SalaireEmployee> findByDateBetween(LocalDate startDate,LocalDate endDate);
    @Query(value = "SELECT MIN(se.date) FROM salaire_employee se", nativeQuery = true)
    LocalDate findOldestSalaryDate();

}
