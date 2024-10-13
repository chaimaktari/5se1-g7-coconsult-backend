package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.Departement;
import com.bezkoder.springjwt.repository.DepartementRepo;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ServiceDepartementTest {

    @MockBean
    private DepartementRepo departementRepo;

    @Autowired
    private ServiceDepartement departmentService;

    @Test
    void testAddDepartment() {
        Departement departement = Departement.builder().id_departement(1L).libelle("anis").maxSaturation(5).nbreEmpl(5).build();
        Mockito.when(departementRepo.save(Mockito.any(Departement.class))).thenReturn(departement);
        long savedDepartement = departmentService.addDepartment(departement);
        verify(departementRepo).save(Mockito.any(Departement.class));

    }

    @Test
    void testCalculateAvailablePercentage() {
        // Arrange
        Departement dep_1 = Departement.builder().id_departement(1L).libelle("anis1").maxSaturation(10).nbreEmpl(8).build();
        Departement dep_2 = Departement.builder().id_departement(2L).libelle("anis2").maxSaturation(15).nbreEmpl(8).build();
        Departement dep_3 = Departement.builder().id_departement(3L).libelle("anis3").maxSaturation(20).nbreEmpl(8).build();

        List<Departement> mockDepartments = Arrays.asList(dep_1, dep_3, dep_2);

        Mockito.when(departementRepo.findAll()).thenReturn(mockDepartments);

        // Act
        ResponseEntity<Double> response = departmentService.calculateAvailablePercentage();

        int expectedAvailablePlaces = (10 - 8) + (15 - 8) + (20 - 8); // 21

        // Assert
        assertEquals(ResponseEntity.ok((double) expectedAvailablePlaces), response);
        assertEquals(21, response.getBody());
    }

}