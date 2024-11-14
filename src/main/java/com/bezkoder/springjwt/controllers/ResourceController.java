package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.IResourcesService;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.ProjectRepository;
import com.bezkoder.springjwt.repository.ResoucesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/resource")
public class ResourceController {
    IResourcesService resourcesService ;
    ProjectRepository projectRepository;
    ResoucesRepository resoucesRepository ;


    @GetMapping("/retrieve-all-resources")
    @ResponseBody
    public List<Resources> getResources() {
        return resourcesService.retrieveAllResources();
    }

    @GetMapping("/retrieve-all-projects")
    @ResponseBody
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }


    @GetMapping("/retrieve-resource/{idRes}")
    @ResponseBody
    public Resources retrieveResource(@PathVariable("idRes") Long idRes) {
        return resourcesService.retrieveResource(idRes);
    }


    @PostMapping(value = "/add-resource", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resources> addResource(@RequestParam("name") String name,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("price") float price,
                                                 @RequestParam("categorie") ResourcesCategorie categorie,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            Resources resource = new Resources();
            resource.setName(name);
            resource.setDescription(description);
            resource.setPrice(price);
            resource.setCategorie(categorie);
            resource.setImage(file.getBytes());

            Resources savedResource = resourcesService.addResource(resource);
            return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update-resource")
    @ResponseBody
    public Resources updateResource(@RequestBody Resources r) {
        return resourcesService.updateResource(r);
    }

    @DeleteMapping("/removeResource/{idRes}")
    @ResponseBody
    public void removeResource(@PathVariable("idRes") Long idRes) {
        Resources resources = resoucesRepository.findById(idRes).get();
        resources.setStock(null);
        resourcesService.removeResource(idRes);
    }



    @PostMapping("/projects/{projectId}")
    public ResponseEntity<?> affectResourcesToProject(@PathVariable("projectId") Long projectId, @RequestBody List<ResourceQuantityDTO> resourceQuantities) {
        try {
            resourcesService.affectResourcesToProject(projectId, resourceQuantities);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{projectId}/resources")
    public ResponseEntity<?> getResourcesForProject(@PathVariable("projectId") Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        List<Resources> resources = project.getResources();
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/resources/{id}/image")
    public ResponseEntity<byte[]> getResourceImage(@PathVariable Long id) {
        Resources resources = resoucesRepository.findById(id).get();
        byte[] imageBytes = resources.getImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("/stock")
    public List<List<Object>> getResourceStockList() {
        return resourcesService.getResourceStockList();
    }

}
