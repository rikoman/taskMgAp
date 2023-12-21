package com.example.backend.api.controller;

import com.example.backend.api.component.MappingResponse;
import com.example.backend.api.component.PageData;
import com.example.backend.story.DTO.PageDataDTO;
import com.example.backend.story.DTO.ProjectDTO;
import com.example.backend.story.entity.Project;
import com.example.backend.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final MappingResponse<Project> mappingResponse;
    private final PageData<Project> pageData;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectDTO dto, Authentication authentication){
        return mappingResponse.entity(projectService.createProject(dto, authentication));
    }

    @GetMapping
    public ResponseEntity<PageDataDTO<Project>> readAllProject(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(projectService.readAllProject(PageRequest.of(page,size))));
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Project> readProjectById(@PathVariable Long id, Authentication authentication) {
        return mappingResponse.entity(projectService.readProjectById(id, authentication));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<PageDataDTO<Project>> readAllProjectByUserId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Authentication authentication
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(projectService.readAllProjectByUserId(id,PageRequest.of(page,size),authentication)));
    }

//    @PutMapping
//    public ResponseEntity<Project> update(@RequestBody Project project){
//        return mappingResponse.entity(projectService.updateProject(project));
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<Project> updatePartInfo(
            @PathVariable Long id,
            @RequestBody ProjectDTO dto,
            Authentication authentication
    ){
        return mappingResponse.entity(projectService.updatePartInfoForProject(id, dto, authentication));
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id, Authentication authentication){
        projectService.deleteProject(id, authentication);
        return HttpStatus.OK;
    }
}