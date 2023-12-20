package com.example.backend.api.service;

import com.example.backend.story.DTO.ProjectDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.User;
import com.example.backend.story.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)
    public Project createProject(ProjectDTO dto){
        Project project = Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .users(userService.readAllUserByIds(dto.getUsersId()))
                .build();
        return projectRepository.save(project);
    }

    @Cacheable(cacheNames = "projects")
    public Page<Project> readAllProject(PageRequest pageRequest){
        return projectRepository.findAll(pageRequest);
    }

    @Cacheable(cacheNames = "project", key = "#id")
    public Project readProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Project with /%s/ id doesn' exist.",id)));
    }

    @Cacheable(cacheNames = "projectsByUserId", key = "#id")
    public Page<Project> readAllProjectByUserId(Long id,PageRequest pageRequest){
        userService.readUserById(id);
        return projectRepository.findByUsersId(id,pageRequest);
    }

//    @Caching(evict = { @CacheEvict(cacheNames = "project", key = "#project.id"),
//                       @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)})
//    public Project updateProject(Project project){
//        if(project.getId() == null || project.getTitle() == null || project.getUsers() == null ){
//            throw new BadRequestException("Invalid request");
//        }
//        return projectRepository.save(project);
//    }

    @Caching(evict = { @CacheEvict(cacheNames = "project", key = "#project.id"),
            @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)})
    public Project updatePartInfoForProject(Long id, ProjectDTO dto){
        Project existProject = readProjectById(id);
        List<User> newUsers = existProject.getUsers();

        if(dto.getTitle()!= null){
            existProject.setTitle(dto.getTitle());
        }

        if(dto.getDescription()!=null){
            existProject.setDescription(dto.getDescription());
        }

        if(dto.getUsersId()!=null){
            newUsers.addAll(userService.readAllUserByIds(dto.getUsersId())); // изменить данное условие
            existProject.setUsers(newUsers);
        }
        return projectRepository.save(existProject);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "project", key = "#id"),
                       @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)})
    public void deleteProject(Long id){
        readProjectById(id);
        projectRepository.deleteById(id);
    }
}
