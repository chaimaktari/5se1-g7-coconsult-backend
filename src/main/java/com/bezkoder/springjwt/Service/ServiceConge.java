package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.IServiceConge;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceConge implements IServiceConge {

    CongeRepo congeRepo;
    EmployeeRepo employeeRepo;

    UserRepository userRepository;

    private JavaMailSender emailSender;

    private static final String ERROR_KEY = "error";


    public  ResponseEntity<?> SendEmailConge(Long id, Conge conge) {
        Employee employee = employeeRepo.findById(id).get();
        List<User> users = userRepository.findAll();
        User user = new User();

        for (User u : users){
            if(employee.getUserId().equals(u.getId())){
                user=u;
            }
        }

        SimpleMailMessage message  = new SimpleMailMessage();
        message.setFrom("gramiaziz9@gmail.com");
        message.setSubject("Your Leave request");
        message.setTo(user.getEmail());
        message.setText("Dear "+user.getUsername()+ "," +
                "\n\nYour Leave request with ID " + conge.getId_conge() +
                " is "+conge.getStatutC()+"."+"\n\nPlease take necessary actions.\n\nSincerely,\nYour Company");
        emailSender.send(message);
        return ResponseEntity.ok( conge.getId_conge());
    }

    @Override
    public Set<Conge> getCongesByEmp(Long id) {
        List<Employee> employees = employeeRepo.findAll();
        Employee employee = new Employee();
        for (Employee u : employees){
            if(u.getUserId().equals(id)){
                employee=u;
            }
        }
        return employee.getConges();
    }

    public boolean isCongeRequestValid(Conge conge, Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).get();

        long differenceInMilliseconds = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        if (employee.getNbrJourConge() < 0 || employee.getNbrJourConge()<differenceInDays
                || conge.getDate_debut().after(conge.getDate_fin())) {
            return false;
        }
        List<Conge> overlappingConges = congeRepo.findCongeInSamePeriodAndSameTeam(
                employee.getTeams().getTeam_id(),
                employee.getPosteEmployee(),
                conge.getDate_debut(),
                conge.getDate_fin());

        return overlappingConges.isEmpty();
    }

    public ResponseEntity<?> saveConge(Conge conge,Long id){
        Employee employee = employeeRepo.findById(id).get();
        if (isCongeRequestValid(conge,id)) {

            conge.setEmployee(employee);
            long differenceInMilliseconds = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);
            employee.setNbrJourConge((int) (employee.getNbrJourConge() - differenceInDays));
            employeeRepo.save(employee);
            congeRepo.save(conge);
            return ResponseEntity.ok( conge.getId_conge());
        }else{
            Map<String, String> errorResponse = new HashMap<>();
            long differenceInMilliseconds = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

            if (employee.getNbrJourConge() < 0 ) {
                errorResponse.put(ERROR_KEY, "there is any days left.");            }
            if ( employee.getNbrJourConge()<differenceInDays
            ) {
                errorResponse.put(ERROR_KEY, "Too much days.");
            }
            if ( conge.getDate_debut().after(conge.getDate_fin())) {
                errorResponse.put(ERROR_KEY, "Invalid conge request. Please check your inputs.");
            }else{
                errorResponse.put(ERROR_KEY, "Check with the administration.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        }

    public boolean isCongeRequestValidUpdate(Conge conge, Long employeeId, Long congeIdToUpdate) {
        Employee employee = employeeRepo.findById(employeeId).get();

        long differenceInMilliseconds = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        if (employee.getNbrJourConge() < 0 || employee.getNbrJourConge()<differenceInDays
                || conge.getDate_debut().after(conge.getDate_fin())) {
            return false;
        }

        List<Conge> overlappingConges = congeRepo.findCongeInSamePeriodAndSameTeam(
                employee.getTeams().getTeam_id(),
                employee.getPosteEmployee(),
                conge.getDate_debut(),
                conge.getDate_fin());

        if (congeIdToUpdate != null) {
            overlappingConges = overlappingConges.stream()
                    .filter(c -> !c.getId_conge().equals(congeIdToUpdate))
                    .toList();
        }

        return overlappingConges.isEmpty();
    }

    @Override
    public ResponseEntity<?> updateConge(Long id,Conge updatedConge) {
        Conge conge = congeRepo.findById(id).get();
        Employee employee = conge.getEmployee();
        if (isCongeRequestValidUpdate(updatedConge,employee.getId_employe(),id)) {
            conge.setCommentaire(updatedConge.getCommentaire());
            conge.setDate_debut(updatedConge.getDate_debut());
            conge.setDate_fin(updatedConge.getDate_fin());
            conge.setStatutC(updatedConge.getStatutC());
            conge.setTypeC(updatedConge.getTypeC());
            congeRepo.save(conge);
            long differenceInMilliseconds = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);
            employee.setNbrJourConge((int) (employee.getNbrJourConge() - differenceInDays));
            employeeRepo.save(employee);
            return ResponseEntity.ok( conge.getId_conge());
        }else{
            Map<String, String> errorResponse = new HashMap<>();
            long differenceInMilliseconds = updatedConge.getDate_fin().getTime() - updatedConge.getDate_debut().getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

            if (employee.getNbrJourConge() < 0 ) {
                errorResponse.put(ERROR_KEY, "there is any days left.");            }
            if ( employee.getNbrJourConge()<differenceInDays
                   ) {
                errorResponse.put(ERROR_KEY, "Too much days.");
            }
            if ( updatedConge.getDate_debut().after(updatedConge.getDate_fin())) {
                errorResponse.put(ERROR_KEY, "Invalid conge request. Please check your inputs.");
            }else{
                errorResponse.put(ERROR_KEY, "Check with the administration.");

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @Override
    public void deleteConge(Long id) {
        Conge conge = congeRepo.findById(id).get();
        Employee employee = conge.getEmployee();
        long differenceInMilliseconds = conge.getDate_fin().getTime() - conge.getDate_debut().getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);
        employee.setNbrJourConge((int) (employee.getNbrJourConge()+differenceInDays));
        employeeRepo.save(employee);
        congeRepo.deleteById(id);
    }

    @Override
    public Conge getConge(Long id) {

        return congeRepo.findById(id).get();
    }
    public List<Conge> filterByStatus(CongeStatut status) {
        String statusString = status.toString(); // Convert enum to string
        return congeRepo.findByStatutC(statusString);
    }
    @Override
    public List<Conge> retrieveAll() {
        return congeRepo.findAll();
    }

    public List<Conge> searchCongesByStartingLetters(String StartingLetter) {
        return congeRepo.findByCommentaireStartingWith(StartingLetter);

    }
    public Map<CongeType, Integer> calculerNombreCongesParType() {
        List<Conge> conges = congeRepo.findAll();

        Map<CongeType, Integer> nombreCongesParType = new EnumMap<>(CongeType.class);

        for (Conge conge : conges) {
            CongeType typeConge = conge.getTypeC();

            if (nombreCongesParType.containsKey(typeConge)) {
                nombreCongesParType.put(typeConge, nombreCongesParType.get(typeConge) + 1);
            } else {
                nombreCongesParType.put(typeConge, 1);
            }
        }
        return nombreCongesParType;
    }

    @Override
    public Long getIdEmplByIdUSer(Long id) {
        List<Employee> employees = employeeRepo.findAll();
        Employee employee = new Employee();
        for (Employee u : employees){
            if(u.getUserId() ==id){
                employee=u;
            }
        }
        return employee.getId_employe();
    }
}