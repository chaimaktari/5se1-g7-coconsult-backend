package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.models.ContratEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ContratEmplRepo extends JpaRepository<ContratEmployee,Long> {
    @Query("SELECT COUNT(e) FROM ContratEmployee e WHERE e.isArchive = false AND e.date_debut BETWEEN :startDate AND :endDate")
    Integer countByIsArchiveIsFalseAndDateDebutBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
