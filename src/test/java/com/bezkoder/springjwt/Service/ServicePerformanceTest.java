package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.repository.EmployeeRepo;
import com.bezkoder.springjwt.repository.NoteRepo;
import com.bezkoder.springjwt.repository.PerfermanceEmplRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.bezkoder.springjwt.models.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ServicePerformanceTest {

    @MockBean
    private NoteRepo noteRepository;

    @MockBean
    private EmployeeRepo employeeRepo;

    @MockBean
    PerfermanceEmplRepo performanceEmployeeRepository;
    @Autowired
    private ServicePerformance servicePerformance;
    @Test
    void getAverageByCriteria() {
        // Arrange
        Object[] average1 = new Object[]{critereNote.Adaptability, 85.0};
        Object[] average2 = new Object[]{critereNote.Communication_skills, 90.0};
        List<Object[]> averages = Arrays.asList(average1, average2);

        Mockito.when(noteRepository.findAverageByCriteria()).thenReturn(averages);

        // Act
        Map<critereNote, Double> result = servicePerformance.getAverageByCriteria();

        // Assert
        assertEquals(2, result.size());
        assertEquals(85.0, result.get(critereNote.Adaptability));
        assertEquals(90.0, result.get(critereNote.Communication_skills));

    }



    @Test
    void savePerformance_ValidAverage() {
        // Arrange
        Long employeeId = 1L;
        PerformanceEmployee performance = new PerformanceEmployee();

        Employee employee = Employee.builder()
                .id_employe(employeeId)
                .hireDate(LocalDate.now())
                .userId(1L)
                .notes(new HashSet<>())
                .build();

        Set<Note> notes = new HashSet<>();
        notes.add(Note.builder().note(85.0f).critere(critereNote.Adaptability).idUser(1L).build());
        notes.add(Note.builder().note(90.0f).critere(critereNote.Communication_skills).idUser(1L).build());
        employee.setNotes(notes);

        Mockito.when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(performanceEmployeeRepository.save(performance)).thenReturn(performance);

        // Act
        PerformanceEmployee savedPerformance = servicePerformance.savePerformance(performance, employeeId);

        // Assert
        assertNotNull(savedPerformance);
        assertEquals(87.5f, savedPerformance.getMoyenne());
        assertEquals("Good", savedPerformance.getCommentaire()); // Adjust based on your implementation
    }

    @Test
    void savePerformance_InvalidAverage() {
        // Arrange
        Long employeeId = 1L;

        Employee employee = Employee.builder()
                .id_employe(employeeId)
                .hireDate(LocalDate.now())
                .userId(1L)
                .notes(new HashSet<>())
                .build();

        Set<Note> notes = new HashSet<>();
        notes.add(Note.builder().note(-10.0f).critere(critereNote.Adaptability).idUser(1L).build());
        employee.setNotes(notes);

        Mockito.when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act/Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicePerformance.savePerformance(new PerformanceEmployee(), employeeId);
        });

        assertEquals("The score must be between 0 and 100.", exception.getMessage());
    }


}