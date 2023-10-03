package com.example.backend.api.service;

import com.example.backend.api.DTO.ProjectDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
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
        if(dto.getTitle() == null || dto.getUserId() == null ){
            throw new BadRequestException("Invalid request");
        }
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
        return projectRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Project with %s id doesn' exist.",id)));
    }

    public List<Project> readAllProjectByUserId(Long id){
        userService.readUserById(id);
        return projectRepository.findByUserId(id);
    }

    public Project updateProject(Project project){
        if(project.getId() == null || project.getTitle() == null || project.getUser() == null ){
            throw new BadRequestException("Invalid request");
        }
        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        readProjectById(id);
        projectRepository.deleteById(id);
    }
}
