package com.example.backend.api.service;

import com.example.backend.api.component.CustomUserPrincipal;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.story.DTO.ProjectDTO;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.User;
import com.example.backend.story.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final CustomUserPrincipal customUserPrincipal;

    @Transactional
//    @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)
    public Project createProject(ProjectDTO dto, Authentication authentication){
        List<Long> projectUsers = new ArrayList<>();
        if(dto.getUsersId()!=null){
            projectUsers.addAll(dto.getUsersId());
        }
        projectUsers.add(customUserPrincipal.getUserDetails(authentication).getId());

        Project project = Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .users(userService.readAllUserByIds(projectUsers))
                .build();

        return projectRepository.save(project);
    }

    @Transactional
//    @Cacheable(cacheNames = "projects")
    public Page<Project> readAllProject(PageRequest pageRequest){
        return projectRepository.findAll(pageRequest);
    }

    @Transactional
//    @Cacheable(cacheNames = "project", key = "#id")
    public Project readProjectById(Long id,Authentication authentication){
        Project existProject = projectRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Project with /%s/ id doesn' exist.",id)));
        if (existProject.getUsers().stream().noneMatch(u -> u.getId().equals(customUserPrincipal.getUserDetails(authentication).getId()))) {
            throw new BadRequestException("Вы не являетесь участником данного проекта");
        }
        return existProject;
    }

    @Transactional
//    @Cacheable(cacheNames = "projectsByUserId", key = "#id")
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

    @Transactional
//    @Caching(evict = { @CacheEvict(cacheNames = "project", key = "#project.id"),
//            @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)})
    public Project updatePartInfoForProject(Long id, ProjectDTO dto, Authentication authentication){
        Project existProject = readProjectById(id,authentication);
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

    @Transactional
//    @Caching(evict = { @CacheEvict(cacheNames = "project", key = "#id"),
//                       @CacheEvict(cacheNames = {"projects", "projectsByUserId"}, allEntries = true)})
    public void deleteProject(Long id, Authentication authentication){
        readProjectById(id,authentication);
        projectRepository.deleteById(id);
    }
}
