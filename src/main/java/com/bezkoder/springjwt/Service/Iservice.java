
        package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface Iservice {
    Project addProject(Project p);
    Project updateProject(Long projectId, Project updatedProject);
    void removeProject(Long project_id);
    List<Project> getAllProjects();
    ResponseEntity<Project> getProjectById(Long id);
    List<Task> getAllTasks ();
    Task addTaskAndAssignToProject(Long projectId, Task task);
    Task updateTask(Long taskId, Task updatedTask);
    ResponseEntity<Task> getTaskById(Long id);
    void deleteTask(Long taskId);
    void removeTask(Long taskId);
    void AssignTaskToEmployee(Long id_employe, Long taskId);
    List<Task> getAllTasksByEmployee(String username);
    List<Task> getTasksByProject(Long projectId) ;
    double calculCostProject (Long projectId);
    double calculateAverageProfitability();
    ResponseEntity<?> calculateStatisticsByType();
    List<Object[]> calculateProfitabilityForEachProject();
    ResponseEntity<?> getBestProjectOfTheYear();


    List<Object[]> calculateProfitabilityByYear();
    Team addTeam(Team team);
    void assignEmployeesToTeam(Set<Employee> employees, Long teamId);
    Team addTeamAndAssignToProject(Team team, Long projectId,Long id);
    double calculateProjectProgression(Long projectId);
    List<Team> getAllTeams();
    Team updateTeam(Long team_id, Team updatedTeam);
    void removeTeam(Long team_id);
    ResponseEntity<Team> getTeamById(Long id);
    Consultant addConsultantAndAssignToProject(Long projectId, Consultant consultant);
    List<User>getproductowners();
    List<Employee> getEmployeesByTeam(Long teamId);
    List<Task> getAllTasksSortedByDueDate();

    Project addProjectandasseignClient(Long idc,Project p);
}



