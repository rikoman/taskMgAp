package com.example.backend.api.controller;

import com.example.backend.api.DTO.CorporateProjectDTO;
import com.example.backend.api.service.CorporateProjectService;
import com.example.backend.story.entity.CorporateProject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corporateProject")
@AllArgsConstructor
public class CorporateProjectController {

    private final CorporateProjectService service;

    @PostMapping
    public ResponseEntity<CorporateProject> create(@RequestBody CorporateProjectDTO corporateProject){
        return mappingResponseCorporateProject(service.createCorporateProject(corporateProject));
    }

    @GetMapping
    public ResponseEntity<List<CorporateProject>> readAllCorporateProjects(){
        return mappingResponseListCorporateProject(service.readAllProject());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<CorporateProject> readCorporateProjectById(@PathVariable Long id){
        return mappingResponseCorporateProject(service.readCorporateProjectById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CorporateProject>> readCorporateProjectByUserId(@PathVariable Long id){
        return mappingResponseListCorporateProject(service.readAllCorporateProjectByUserId(id));
    }

    @PutMapping
    public ResponseEntity<CorporateProject> update(@RequestBody CorporateProject corporateProject){
        return mappingResponseCorporateProject(service.updateCorporateProject(corporateProject));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        service.deleteCorporateProject(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<CorporateProject> mappingResponseCorporateProject(CorporateProject corporateProject){
        return new ResponseEntity<>(corporateProject,HttpStatus.OK);
    }

    private ResponseEntity<List<CorporateProject>> mappingResponseListCorporateProject(List<CorporateProject> corporateProjects){
        return new ResponseEntity<>(corporateProjects,HttpStatus.OK);
    }
}