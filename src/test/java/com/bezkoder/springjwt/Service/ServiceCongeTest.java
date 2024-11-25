package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Conge;
import com.bezkoder.springjwt.models.Employee;
import com.bezkoder.springjwt.models.PosteEmployee;
import com.bezkoder.springjwt.models.Team;
import com.bezkoder.springjwt.repository.CongeRepo;
import com.bezkoder.springjwt.repository.EmployeeRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ServiceCongeTest {
 /*   @MockBean
    private EmployeeRepo employeeRepo;
    @MockBean
    private CongeRepo congeRepo;
    @Autowired
    private ServiceConge serviceConge;

    private Employee employeeAnis;
    private Conge conge;
    private Team teamAnis;
    @BeforeEach
    void setUp() {
        teamAnis = Team.builder()
                .team_id(1L)
                .team_name("Team Anis")
                .availability(true)
                .nbteam(5)
                .build();

         employeeAnis = Employee.builder()
                .id_employe(1L)
                .nbrJourConge(10)
                .teams(teamAnis)
                .PosteEmployee(PosteEmployee.ACCOUNTANT)
                .build();
         conge = Conge.builder()
                .date_debut(new Date(System.currentTimeMillis()))
                .date_fin(new Date(System.currentTimeMillis() + 86400000))
                .build();
    }

    @Test
    @Order(1)
    void testIsCongeRequestValid_ValidRequest() {
        // Arrange
        Long employeeId = 1L;

        Mockito.when(employeeRepo.findById(employeeId)).thenReturn(Optional.ofNullable(employeeAnis));
        Mockito.when(congeRepo.findCongeInSamePeriodAndSameTeam(any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        boolean result = serviceConge.isCongeRequestValid(conge, employeeId);

        // Assert
        assertTrue(result);
    }

    @Test
    @Order(2)
    void testIsCongeRequestValid_OverlappingRequest() {
        // Arrange
        Long employeeId = 1L;

        Mockito.when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employeeAnis));
        Mockito.when(congeRepo.findCongeInSamePeriodAndSameTeam(any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(conge));

        // Act
        boolean result = serviceConge.isCongeRequestValid(conge, employeeId);

        // Assert
        assertFalse(result);
    }

    @Test
    @Order(3)
    void testIsCongeRequestValid_InvalidDates() {
        // Arrange
        Long employeeId = 1L;
        conge.setDate_fin(new Date(System.currentTimeMillis() - 86400000));
        Mockito.when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employeeAnis));

        // Act
        boolean result = serviceConge.isCongeRequestValid(conge, employeeId);

        // Assert
        assertFalse(result);
    }
    @Test
    @Order(4)
    void testSaveConge_ValidRequest() {
        // Arrange
        Long employeeId = 1L;

        Mockito.when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employeeAnis));
        Mockito.when(congeRepo.findCongeInSamePeriodAndSameTeam(any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());
        Mockito.when(congeRepo.save(conge)).thenReturn(conge);

        // Act
        ResponseEntity<?> response = serviceConge.saveConge(conge, employeeId);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conge.getId_conge(), response.getBody());
        assertEquals(9, employeeAnis.getNbrJourConge());
    }

*/

}