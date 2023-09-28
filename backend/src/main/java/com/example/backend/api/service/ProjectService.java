package com.example.backend.api.service;

import com.example.backend.api.DTO.ProjectDTO;
import com.example.backend.story.entity.Project;
import com.example.backend.story.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public Project createProject(ProjectDTO dto){
        Project project = Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .user(userService.readUserById(dto.getUserId()))
                .build();
        return projectRepository.save(project);
    }

    public List<Project> readAllProject(){
        return projectRepository.findAll();
    }

    public Project readProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(()->new RuntimeException("Project not found"));
    }

    public List<Project> readAllProjectByUserId(Long id){
        return projectRepository.findByUserId(id);
    }

    public Project updateProject(Project project){
        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
