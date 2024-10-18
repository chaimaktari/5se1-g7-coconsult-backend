//package com.bezkoder.springjwt;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import com.bezkoder.springjwt.Service.RhService;
//import com.bezkoder.springjwt.models.Recrutement;
//import com.bezkoder.springjwt.models.StatutRecrut;
//import com.bezkoder.springjwt.repository.RecrutementRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//@SpringBootTest
//public class RecrutementServiceTest {
//
//    @Autowired
//    private RhService recrutementService;
//
//    @MockBean
//    private RecrutementRepo recrutementRepo;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testMajstatut() {
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDate dateCloture = LocalDate.now().minusDays(1);
//
//        Recrutement recrutement1 = new Recrutement();
//        recrutement1.setDateCloture(dateCloture);
//        recrutement1.setStatutRecrut(StatutRecrut.OUVERTE);
//
//        Recrutement recrutement2 = new Recrutement();
//        recrutement2.setDateCloture(LocalDate.now().plusDays(1));
//        recrutement2.setStatutRecrut(StatutRecrut.OUVERTE);
//
//        List<Recrutement> recrutements = new ArrayList<>();
//        recrutements.add(recrutement1);
//        recrutements.add(recrutement2);
//
//        when(recrutementRepo.findAllByStatutRecrutement(StatutRecrut.OUVERTE)).thenReturn(recrutements);
//
//        recrutementService.Majstatut();
//
//
//        verify(recrutementRepo).saveAll(anyList());
//        assertEquals(StatutRecrut.CLOTURE, recrutement1.getStatutRecrut());
//        assertEquals(StatutRecrut.OUVERTE, recrutement2.getStatutRecrut());
//    }
//}
////njib data ml base w nbdlha assert ll date