package com.example.backend.service;

import com.example.backend.DTO.ProjectDTO;
import com.example.backend.entity.Project;
import com.example.backend.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project createProject(ProjectDTO dto){
        Project project = Project.builder()
                .title(dto.getTitle())
                .status(dto.isStatus())
                .description(dto.getDescription())
                .build();
        return projectRepository.save(project);
    }

    public List<Project> readAllProject(){
        return projectRepository.findAll();
    }

    public Project updateProject(Project project){
        return projectRepository.save(project);
    }

    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
