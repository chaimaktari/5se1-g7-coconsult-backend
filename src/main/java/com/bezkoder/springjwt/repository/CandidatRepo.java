package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepo extends JpaRepository<Candidat, Long> {
    Candidat getCandByidCandidat (Long idCandidat);


    @Query("SELECT c FROM Candidat c WHERE c.StatutCandidat = 'SELECTIONNE'")
    List<Candidat> findAllSelectionnes();


    @Query("SELECT c.email FROM Candidat c WHERE c.idCandidat = :candidatId")
    List<String> findEmailsByIdCandidat(Long candidatId);

    @Query("SELECT c FROM Candidat c WHERE c.StatutCandidat = 'EN_ATTENTE' AND c.Datepostule <= :dateLimite")
    List<Candidat> findAllSelectionnesPlusDeDeuxJours(LocalDate dateLimite);


    @Query("SELECT c FROM Candidat c JOIN c.detailRecrutement dr WHERE c.StatutCandidat = 'ACCEPTE' AND dr.dateEntretien IS NULL AND c.Datepostule <= :dateLimite")
    List<Candidat> findAcceptedCandidatsWithoutDateEntretien(LocalDate dateLimite);

    Optional<Candidat> findByEmail(String email);
}