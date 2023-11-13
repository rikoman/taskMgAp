package com.example.backend.story.repository;

import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Comment;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
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
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private  TaskRepository taskRepository;

    private Comment comment;

    private final Long id = 1l;

    private final String content = "Content";

    private final Project project = Project.builder()
            .title("Project Title")
            .build();

    private final Category category = Category.builder()
            .title("category Title")
            .project(project)
            .build();

    private final Task task = Task.builder()
            .title("title")
            .project(project)
            .build();

    @BeforeEach
    void prepareDate(){
        comment = Comment.builder()
                .id(id)
                .content(content)
                .project(project)
                .category(category)
                .task(task)
                .build();

        projectRepository.save(project);

        categoryRepository.save(category);

        taskRepository.save(task);
    }

    @Test
    void saveTest(){
        Comment savedComment = repository.save(comment);

        Comment comment1 = repository.findById(id).orElseThrow();

        assertEquals(savedComment.getId(),comment1.getId());
        assertEquals(savedComment.getContent(),comment1.getContent());
        assertEquals(savedComment.getProject(),comment1.getProject());
        assertEquals(savedComment.getCategory(),comment1.getCategory());
        assertEquals(savedComment.getTask(),comment1.getTask());
    }

    @Test
    void findAllTest(){
        Comment comment1 = Mockito.mock(Comment.class);
        Comment comment2 = Mockito.mock(Comment.class);
        Comment comment3 = Mockito.mock(Comment.class);

        repository.saveAll(List.of(comment1,comment2,comment3));

        List<Comment> result = repository.findAll();

        assertEquals(3,result.size());
    }

    @Test
    void updateTest(){
        Comment savedComment = repository.save(comment);

        String newContent = "new Content";
        Project newProject = Project.builder()
                .title("Project Title")
                .build();

        Category newCategory = Category.builder()
                .title("category Title")
                .project(project)
                .build();

        Task newTask = Task.builder()
                .title("title")
                .project(project)
                .build();

        savedComment.setId(id);
        savedComment.setContent(newContent);
        savedComment.setProject(newProject);
        savedComment.setCategory(newCategory);
        savedComment.setTask(newTask);

        Comment updatedComment = repository.save(savedComment);

        assertEquals(id,updatedComment.getId());
        assertEquals(newContent,updatedComment.getContent());
        assertEquals(newProject,updatedComment.getProject());
        assertEquals(newCategory,updatedComment.getCategory());
        assertEquals(newTask,updatedComment.getTask());
    }

    @Test
    void deleteByIdTest(){
        Comment savedComment = repository.save(comment);

        repository.deleteById(savedComment.getId());

        Optional<Comment> comment1 = repository.findById(savedComment.getId());

        assertTrue(comment1.isEmpty());
    }

    @Test
    void findByProjectId() {
        Comment savedComment = repository.save(comment);

        List<Comment> result = repository.findByProjectId(savedComment.getProject().getId());

        assertEquals(1, result.size());
    }

    @Test
    void findByCategoryId() {
        Comment savedComment = repository.save(comment);

        List<Comment> result = repository.findByCategoryId(savedComment.getCategory().getId());

        assertEquals(1, result.size());
    }

    @Test
    void findByTaskId() {
        Comment savedComment = repository.save(comment);

        List<Comment> result = repository.findByTaskId(savedComment.getTask().getId());

        assertEquals(1, result.size());
    }
}