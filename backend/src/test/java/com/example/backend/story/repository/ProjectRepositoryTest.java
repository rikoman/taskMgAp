package com.example.backend.story.repository;

import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private UserRepository userRepository;

    private Project project;

    private final Long id = 1l;

    private final String title = "title";

    private final String description = "description";

    private final User user = new User("Roman","abvd","roma@gmail.ru");

    @BeforeEach
    void prepareDate(){
        project = Project.builder()
                .id(id)
                .title(title)
                .description(description)
                .users(List.of(user))
                .build();

        userRepository.save(user);
    }

    @Test
    void saveTest(){
        repository.save(project);

        Project project1 = repository.findById(id).orElseThrow();

        assertEquals(id,project1.getId());
        assertEquals(title,project1.getTitle());
        assertEquals(description,project1.getDescription());
        assertEquals(List.of(user),project1.getUsers());
    }

    @Test
    void findAllTest(){
        Project project1 = Mockito.mock(Project.class);
        Project project2 = Mockito.mock(Project.class);
        Project project3 = Mockito.mock(Project.class);

        repository.saveAll(List.of(project1,project2,project3));

        List<Project> result = repository.findAll();

        assertEquals(3,result.size());
    }

    @Test
    void updateTest(){
        Project savedProject = repository.save(project);

        String newTitle = "new Title";
        String newDescription = "new Description";

        savedProject.setId(id);
        savedProject.setTitle(newTitle);
        savedProject.setDescription(newDescription);

        Project updatedProject = repository.save(savedProject);

        assertEquals(id,updatedProject.getId());
        assertEquals(newTitle,updatedProject.getTitle());
        assertEquals(newDescription,updatedProject.getDescription());
    }

    @Test
    void deleteByIdTest(){
        repository.save(project);

        repository.deleteById(id);

        Optional<Project> project1 = repository.findById(id);

        assertTrue(project1.isEmpty());
    }

    @Test
    void findByUsersIdTest() {
        Project project1 = Project.builder()
                .id(2l)
                .title(title)
                .description(description)
                .users(List.of(user))
                .build();

        repository.save(project1);

        List<Project> result = repository.findByUsersId(user.getId());
        assertNotNull(result);
    }
}