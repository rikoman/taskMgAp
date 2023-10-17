package com.example.backend.api.service;

import com.example.backend.story.DTO.ProjectDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Project;
import com.example.backend.story.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    @CacheEvict(cacheNames = "projects", allEntries = true)
    public Project createProject(ProjectDTO dto){
        if(dto.getTitle() == null || dto.getUsersId() == null ){
            throw new BadRequestException("Invalid request");
        }
        Project project = Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .users(userService.readAllUserByIds(dto.getUsersId()))
                .build();
        return projectRepository.save(project);
    }

    @Cacheable(cacheNames = "projects")
    public List<Project> readAllProject(){
        return projectRepository.findAll();
    }

    @Cacheable(cacheNames = "project", key = "#id")
    public Project readProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Project with %s id doesn' exist.",id)));
    }

    @Cacheable(cacheNames = "projectsByUserId", key = "#id")
    public List<Project> readAllProjectByUserId(Long id){
        userService.readUserById(id);
        return projectRepository.findByUsersId(id);
    }

    @CacheEvict(cacheNames = "projects", allEntries = true)
    public Project updateProject(Project project){
        if(project.getId() == null || project.getTitle() == null || project.getUsers() == null ){
            throw new BadRequestException("Invalid request");
        }
        return projectRepository.save(project);
    }
    @Caching(evict = { @CacheEvict(cacheNames = "project", key = "#id"),
                       @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)})
    public void deleteProject(Long id){
        readProjectById(id);
        projectRepository.deleteById(id);
    }
}
