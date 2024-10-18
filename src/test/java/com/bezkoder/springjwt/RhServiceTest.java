//package com.bezkoder.springjwt;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.bezkoder.springjwt.Service.RhService;
//import com.bezkoder.springjwt.models.Candidat;
//import com.bezkoder.springjwt.models.Recrutement;
//import com.bezkoder.springjwt.models.StatutCandidat;
//import com.bezkoder.springjwt.repository.CandidatRepo;
//import com.bezkoder.springjwt.repository.RecrutementRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//class RhServiceTest {
//
//    @Autowired
//    private RhService candidatService;
//    @Autowired
//    private CandidatRepo candidatRepo;
//    @Autowired
//    private RecrutementRepo recrutementRepo;
//    private Candidat candidat;
//    private Recrutement recrutement;
//    @BeforeEach
//    void setUp() {
//        candidat = new Candidat();
//        candidat.setIdCandidat(1L);
//        candidat.setNom("John Doe");
//        recrutement = new Recrutement();
//        recrutement.setIdRec(1L);
//        recrutement.setPostesVacants(2);
//        candidatRepo.save(candidat);
//        recrutementRepo.save(recrutement);
//    }
//
//    @Test
//     void testEvaluerCandidat_Selectionne() {
//        Integer score = 70;
//        candidatService.evaluerCandidat(candidat.getIdCandidat(), recrutement.getIdRec(), score);
//        Candidat updatedCandidat = candidatRepo.findById(candidat.getIdCandidat()).get();
//        Recrutement updatedRecrutement = recrutementRepo.findById(recrutement.getIdRec()).get();
//        assertEquals(70, updatedCandidat.getScore());
//        assertEquals(StatutCandidat.SELECTIONNE, updatedCandidat.getStatutCandidat());
//        assertEquals(1, updatedRecrutement.getPostesVacants());
//    }
//
//    @Test
//     void testEvaluerCandidat_Refuse() {
//        Integer score = 30;
//        candidatService.evaluerCandidat(candidat.getIdCandidat(), recrutement.getIdRec(), score);
//        Candidat updatedCandidat = candidatRepo.findById(candidat.getIdCandidat()).get();
//        Recrutement updatedRecrutement = recrutementRepo.findById(recrutement.getIdRec()).get();
//        assertEquals(30, updatedCandidat.getScore());
//        assertEquals(StatutCandidat.REFUSE, updatedCandidat.getStatutCandidat());
//        assertEquals(2, updatedRecrutement.getPostesVacants());
//    }
//
//    @Test
//     void testEvaluerCandidat_NullScore() {
//        Integer score = null;
//        candidatService.evaluerCandidat(candidat.getIdCandidat(), recrutement.getIdRec(), score);
//        Candidat updatedCandidat = candidatRepo.findById(candidat.getIdCandidat()).get();
//        Recrutement updatedRecrutement = recrutementRepo.findById(recrutement.getIdRec()).get();
//        assertEquals(StatutCandidat.REFUSE, updatedCandidat.getStatutCandidat());
//        assertEquals(2, updatedRecrutement.getPostesVacants());
//    }
//
//
//}
