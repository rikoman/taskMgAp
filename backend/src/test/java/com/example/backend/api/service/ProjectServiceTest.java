package com.example.backend.api.service;

import com.example.backend.api.exception.BadRequestException;
import com.example.backend.story.DTO.ProjectDTO;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.user.User;
import com.example.backend.story.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserService userService;

    private final ProjectDTO projectDTO = new ProjectDTO();

    private List<Long> userIds;

    private final User user1 = new User();

    private final User user2 = new User();

    private Project project;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareDate(){
        user1.setId(1L);
        user2.setId(2L);

        projectDTO.setTitle("title");
        projectDTO.setDescription("Description");
        projectDTO.setUsersId(List.of(user1.getId(),user2.getId()));


        project = Project.builder()
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .users(Arrays.asList(user1, user2))
                .build();
    }

    @Test
    void createProject_invalidRequest_throwBadRequestException() {
        projectDTO.setTitle(null);
        projectDTO.setUsersId(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> projectService.createProject(projectDTO));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createProject_validRequest_saveProject() {
        projectService.createProject(projectDTO);

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void readAllProjectTest() {
        projectService.readAllProject();

        verify(projectRepository,times(1)).findAll();
    }

    @Test
    void readProjectByIdTest() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        projectService.readProjectById(project.getId());

        verify(projectRepository, times(1)).findById(project.getId());
    }

    @Test
    void readAllProjectByUserIdTest() {
        projectService.readAllProjectByUserId(user1.getId());

        verify(projectRepository, times(1)).findByUsersId(user1.getId());
    }

    @Test
    void updateProject_invalidRequest_throwBadRequestException() {
        Project project = new Project();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> projectService.updateProject(project));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void updateProject_validRequest_updateProject() {
        project.setId(1L);

        projectService.updateProject(project);

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void updatePartInfoForProjectTest() {
        List<User> existingUsers = new ArrayList<>();

        Project existingProject = Project.builder()
                .id(project.getId())
                .description("Existing Description")
                .users(existingUsers)
                .build();

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(existingProject));

        projectService.updatePartInfoForProject(project);

        verify(projectRepository).findById(project.getId());
        verify(projectRepository).save(existingProject);
    }

    @Test
    void deleteProjectTest() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        projectService.deleteProject(project.getId());

        verify(projectRepository, times(1)).deleteById(project.getId());
    }
}