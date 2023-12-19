package com.example.backend.api.controller;

import com.example.backend.api.component.MappingResponse;
import com.example.backend.api.component.PageData;
import com.example.backend.story.DTO.PageDataDTO;
import com.example.backend.story.DTO.ProjectDTO;
import com.example.backend.story.entity.Project;
import com.example.backend.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final MappingResponse<Project> mappingResponse;
    private final PageData<Project> pageData;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectDTO dto){
        return mappingResponse.entity(projectService.createProject(dto));
    }

    @GetMapping
    public ResponseEntity<PageDataDTO<Project>> readAllProject(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(projectService.readAllProject(PageRequest.of(page,size))));
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Project> readProjectById(@PathVariable Long id) {
        return mappingResponse.entity(projectService.readProjectById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<PageDataDTO<Project>> readAllProjectByUserId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(projectService.readAllProjectByUserId(id,PageRequest.of(page,size))));
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project){
        return mappingResponse.entity(projectService.updateProject(project));
    }

    @PatchMapping
    public ResponseEntity<Project> updatePartInfo(@RequestBody Project project){
        return mappingResponse.entity(projectService.updatePartInfoForProject(project));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        projectService.deleteProject(id);
        return HttpStatus.OK;
    }
}