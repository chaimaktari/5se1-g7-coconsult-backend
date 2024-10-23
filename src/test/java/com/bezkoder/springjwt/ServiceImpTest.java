package com.bezkoder.springjwt;

import com.bezkoder.springjwt.Service.ServiceImp;
import com.bezkoder.springjwt.models.Project;
import com.bezkoder.springjwt.models.Task;
import com.bezkoder.springjwt.models.TaskStatus;
import com.bezkoder.springjwt.repository.ProjectRepository;
import lombok.Builder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest

public class ServiceImpTest {

     /*@Mock
     private ProjectRepository projectRepository;

     @InjectMocks
     private ServiceImp serviceImp;

     private Project miniProject;


     @BeforeEach
     public void setup() {
          MockitoAnnotations.openMocks(this);

          // Création d'un projet mock
         miniProject = new Project();
         miniProject.setTasks(new ArrayList<>());
     }


     @Test
     public void testCalculateProjectProgression(){
          // Configurez le comportement du mock pour renvoyer un projet avec des tâches
          Task task1 = new Task();
          task1.setStartDate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)); // 3 jours
          task1.setEndDate(new Date()); // Aujourd'hui
          task1.setStatus(TaskStatus.DONE); // Tâche terminée

          Task task2 = new Task();
          task2.setStartDate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 5)); // 5 jours
          task2.setEndDate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)); // 2 jours
          task2.setStatus(TaskStatus.IN_PROGRESS); // Tâche non terminée

          // Ajoutez les tâches au projet
         miniProject.getTasks().add(task1);
         miniProject.getTasks().add(task2);

         // Configurez le mock pour renvoyer le projet
          when(projectRepository.findById(1L)).thenReturn(Optional.of(miniProject));

          // Exécutez la méthode à tester
          double progression = serviceImp.calculateProjectProgression(1L);

          Assertions.assertEquals(50.0, progression);
         System.out.println("Progression est : " + progression);


     }*/


















@Autowired
    private ServiceImp serviceImp;

   @Test
    public void testCalculateProjectProgressionn(){
      //creation du projet
        Project project = Project.builder().projectid(1L).projectname("Project1").projectdescription("Description du projet").endDate(java.sql.Date.valueOf(LocalDate.parse("2024-12-31"))).startDate(java.sql.Date.valueOf(LocalDate.parse("2024-01-01"))).build();

        // Création des tâches associées au projet
        Task task1 = Task.builder()
                .taskname("Task 1")
                .taskdescription("Description Task 1")
                .startDate(java.sql.Date.valueOf(LocalDate.parse("2024-01-01")))
                .endDate(java.sql.Date.valueOf(LocalDate.parse("2024-02-01")))
                .status(TaskStatus.DONE)
                .build();

        Task task2 = Task.builder()
                .taskname("Task 2")
                .taskdescription("Description Task 2")
                .startDate(java.sql.Date.valueOf(LocalDate.parse("2024-03-01")))
                .endDate(java.sql.Date.valueOf(LocalDate.parse("2024-04-01")))
                .status(TaskStatus.IN_PROGRESS)
                .build();

        // Ajouter les tâches au projet
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        project.setTasks(tasks);

        // Act : Sauvegarde du projet
        Project savedProject = serviceImp.addProject(project);
        Task t1 = serviceImp.addTaskAndAssignToProject(1L,task1);
       Task t2 = serviceImp.addTaskAndAssignToProject(1L,task2);


        Assertions.assertNotNull(savedProject.getProjectid());
        // Appelez la méthode à tester
        double progression = serviceImp.calculateProjectProgression(project.getProjectid());

        // Vérifiez le résultat attendu
        assertEquals(50.0, progression, 0.01); // 50% de progression

}













}

