package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementRepo extends JpaRepository<Departement,Long> {
    List<Departement> findByLibelleStartingWith(String startingLetter);
    List<Departement> findByLibelleStartingWithAndNbreEmplAndMaxSaturation(String startingLetter, int nbreEmployees, float maxSaturation);

    Departement findByLibelle(String libelle);

}
