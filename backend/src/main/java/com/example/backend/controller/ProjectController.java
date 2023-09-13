package com.example.backend.controller;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.entity.Project;
import com.example.backend.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    public ResponseEntity<Project> create (@RequestBody ProjectDTO dto){
        return new ResponseEntity<>(projectService.createProject(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Project>> readAllProject(){
        return new ResponseEntity<>(projectService.readAllProject(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project){
        return new ResponseEntity<>(projectService.updateProject(project),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        projectService.deleteProject(id);
        return HttpStatus.OK;
    }
}
