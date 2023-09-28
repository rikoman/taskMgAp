package com.example.backend.api.controller;

import com.example.backend.api.DTO.ProjectDTO;
import com.example.backend.story.entity.Project;
import com.example.backend.api.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> create (@RequestBody ProjectDTO dto){
        return mappingResponseProject(projectService.createProject(dto));
    }

    @GetMapping
    public ResponseEntity<List<Project>> readAllProject(){
        return mappingResponseListProject(projectService.readAllProject());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Project> readProjectById(@PathVariable Long id) {
        return mappingResponseProject(projectService.readProjectById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Project>> readAllProjectByUserId(@PathVariable Long id){
        return mappingResponseListProject(projectService.readAllProjectByUserId(id));
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project){
        return mappingResponseProject(projectService.updateProject(project));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        projectService.deleteProject(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Project> mappingResponseProject(Project project){
        return new ResponseEntity<>(project,HttpStatus.OK);
    }

    private ResponseEntity<List<Project>> mappingResponseListProject(List<Project> project){
        return new ResponseEntity<>(project,HttpStatus.OK);
    }
}
