package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.DTO.TeamDTO;
import com.bezkoder.springjwt.Service.Iservice;
import com.bezkoder.springjwt.models.*;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@AllArgsConstructor


@RequestMapping("/coconsult")
public class Controller {
    Iservice iservice;

    @PostMapping("/addProject")
    @ResponseBody
    public Project addProject(@RequestBody Project p) {
        return iservice.addProject(p);
    }

    @PutMapping("/updateproject/{idProject}")
    @ResponseBody
    public Project updateProject(@PathVariable("idProject") Long idProject, @RequestBody Project p) {
        return iservice.updateProject(idProject, p);
    }

    @DeleteMapping("/removeProject/{idProject}")
    @ResponseBody
    public void removeProject(@PathVariable("idProject") Long project_id) {
        iservice.removeProject(project_id);
    }


    @GetMapping("/getAll")
    @ResponseBody
    public List<Project> getAllProjects() {
        return iservice.getAllProjects();
    }

    @GetMapping("/getAllT")
    @ResponseBody
    public List<Task> getAllTasks() {
        return iservice.getAllTasks();
    }

    @DeleteMapping("/removetask/{idtask}")
    @ResponseBody
    public void removeTask(@PathVariable("idtask") Long idtask) {
        iservice.removeTask(idtask);
    }


    @PostMapping("/add-task/{projectId}")
    public Task addTaskAndAssignToProject(@PathVariable Long projectId, @RequestBody Task task) {
        return iservice.addTaskAndAssignToProject(projectId, task);
    }


    @PutMapping("/updatetask/{idTask}")
    @ResponseBody
    public Task updateTask(@PathVariable("idTask") Long idTask, @RequestBody Task t) {
        return iservice.updateTask(idTask, t);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return iservice.getTaskById(id);
    }


    @PostMapping("/AssignTaskToEmployee/{id_employe}/{taskId}")
    public void AssignTaskToEmployee(@PathVariable Long id_employe, @PathVariable Long taskId) {
        iservice.AssignTaskToEmployee(id_employe, taskId);
    }

    @GetMapping("/gettaskbyemployee/{username}")
    @ResponseBody
    List<Task> getAllTasksByEmployee(@PathVariable String username) {
        return iservice.getAllTasksByEmployee(username);
    }

    @GetMapping("/gettaskbyproject/{projectId}")
    @ResponseBody
    List<Task> getTasksByProject(@PathVariable Long projectId) {
        return iservice.getTasksByProject(projectId);
    }

    @PutMapping("/calculate-cost/{projectId}")
    public double calculCostProject(@PathVariable Long projectId) {
        return iservice.calculCostProject(projectId);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return iservice.getProjectById(id);
    }


    @GetMapping("/average-profitability")
    public double calculateAverageProfitability() {
        return iservice.calculateAverageProfitability();
    }

    @GetMapping("/statisticsByType")
    public ResponseEntity<?> getStatisticsByType() {
        return iservice.calculateStatisticsByType();
    }
    @GetMapping("/calculateProfitability")
    public List<Object[]> calculateProfitabilityForEachProject() {
        return iservice.calculateProfitabilityForEachProject();
    }

    @GetMapping("/best-project")
    public ResponseEntity<?> getBestProjectOfTheYear(){
        return iservice.getBestProjectOfTheYear();
    }


    @GetMapping("/profitability-by-year")
    public List<Object[]> getProfitabilityByYear() {
        return iservice.calculateProfitabilityByYear();
    }


    @PostMapping("/addTeam")
    @ResponseBody
    public Team addTeam(@RequestBody Team team) {
        return iservice.addTeam(team);
    }

    @PutMapping("/assign-employees/{teamId}")
    public void assignEmployeesToTeam(@RequestBody Set<Employee> employees,@PathVariable("teamId") Long teamId) {
        iservice.assignEmployeesToTeam(employees,teamId);
    }



    @GetMapping("/progression/{projectId}")
    public ResponseEntity<Double> calculateProjectProgression(@PathVariable Long projectId) {
        try {
            double progression = iservice.calculateProjectProgression(projectId);
            return ResponseEntity.ok(progression);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllTeams")
    @ResponseBody
    public List<TeamDTO> getAllTeams() {
        List<Team> teamList = iservice.getAllTeams();
        return teamList.stream().map((team -> TeamDTO.toDto(team))).toList();
    }

    @PutMapping("/updateteam/{team_id}")
    @ResponseBody
    public Team updateTeam(@PathVariable("team_id") Long team_id, @RequestBody Team t) {
        return iservice.updateTeam(team_id, t);
    }

    @DeleteMapping("/removeteam/{team_id}")
    @ResponseBody
    public void removeTeam(@PathVariable("team_id") Long team_id) {
        iservice.removeTeam(team_id);
    }
    @GetMapping("/team/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return iservice.getTeamById(id);
    }

    @PostMapping("/addconsultant/{projectId}")
    public Consultant addConsultantAndAssignToProject(@PathVariable Long projectId, @RequestBody Consultant consultant) {
        return iservice.addConsultantAndAssignToProject(projectId, consultant);
    }
    @GetMapping("/getPo")
    public List<User> getallPO(){
        return iservice.getproductowners();
    }
    @PostMapping("/addteamaff/{projectId}/{iduser}")
    public Team addTeamAndAssignToProject(@PathVariable Long projectId, @RequestBody Team team,@PathVariable Long iduser) {
        return iservice.addTeamAndAssignToProject(team,projectId,iduser);
    }
    @GetMapping("/getempl/{TeamId}")
    public List<Employee> getEmployeeByTeam(@PathVariable Long TeamId){
        return iservice.getEmployeesByTeam(TeamId);
    }


    @GetMapping("/sorted")
    public List<Task> getSortedTasks() {
        return iservice.getAllTasksSortedByDueDate();
    }

    @PostMapping("/addandasseignclient/{idc}")
    public Project addandasseignclient(@PathVariable Long idc,@RequestBody Project p){
        return iservice.addProjectandasseignClient(idc,p);
    }
}
