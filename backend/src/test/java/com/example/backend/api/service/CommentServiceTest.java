package com.example.backend.api.service;

import com.example.backend.api.exception.BadRequestException;
import com.example.backend.story.DTO.CommentDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Comment;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private TaskService taskService;

    private final Project project = Project.builder()
            .id(1L)
            .title("Project Title")
            .build();

    private final Category category = Category.builder()
            .id(1L)
            .title("category Title")
            .project(project)
            .build();

    private final Task task = Task.builder()
            .id(1L)
            .title("title")
            .project(project)
            .build();

    private Comment comment;

    private final CommentDTO dto = new CommentDTO();
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareDate(){
        comment = Comment.builder()
                .id(1L)
                .content("Content")
                .project(project)
                .build();
    }

    @Test
    void createCommentWithContentNullTest() {
        dto.setCategoryId(category.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> commentService.createComment(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createCommentWithProjectIdAndCategoryIdNotNullTest() {
        dto.setContent("Content");
        dto.setProjectId(project.getId());
        dto.setCategoryId(category.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> commentService.createComment(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createCommentWithProjectIdAndTaskIdNotNullTest() {
        dto.setContent("Content");
        dto.setProjectId(project.getId());
        dto.setTaskId(task.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> commentService.createComment(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createCommentWithCategoryIdAndTaskIdNotNullTest() {
        dto.setContent("Content");
        dto.setCategoryId(category.getId());
        dto.setTaskId(task.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> commentService.createComment(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createComment_Valid_WithProjectTest() {
        dto.setContent("Content");
        dto.setProjectId(project.getId());

        commentService.createComment(dto);

        verify(commentRepository,times(1)).save(any(Comment.class));
    }

    @Test
    void createComment_Valid_WithCategoryTest() {
        dto.setContent("Content");
        dto.setCategoryId(category.getId());

        commentService.createComment(dto);

        verify(commentRepository,times(1)).save(any(Comment.class));
    }

    @Test
    void createComment_Valid_WithTaskTest() {
        dto.setContent("Content");
        dto.setTaskId(task.getId());

        commentService.createComment(dto);

        verify(commentRepository,times(1)).save(any(Comment.class));
    }

    @Test
    void readAllCommentTest() {
        commentService.readAllComment();

        verify(commentRepository,times(1)).findAll();

    }

    @Test
    void readAllCommentByProjectIdTest() {
        commentService.readAllCommentByProjectId(comment.getId());

        verify(commentRepository, times(1)).findByProjectId(comment.getId());
    }

    @Test
    void readAllCommentByCategoryIdTest() {
        commentService.readAllCommentByCategoryId(comment.getId());

        verify(commentRepository, times(1)).findByCategoryId(comment.getId());
    }

    @Test
    void readAllCommentByTaskIdTest() {
        commentService.readAllCommentByTaskId(comment.getId());

        verify(commentRepository, times(1)).findByTaskId(comment.getId());
    }

    @Test
    void updateCommentTest() {
        comment.setId(1L);

        commentService.updateComment(comment);

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void updatePartInfoForCommentTest() {
        Comment existingComment = Comment.builder()
                .id(comment.getId())
                .content("Content")
                .project(project)
                .build();

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(existingComment));

        commentService.updatePartInfoForComment(comment);

        verify(commentRepository).findById(comment.getId());
        verify(commentRepository).save(existingComment);
    }

    @Test
    void deleteCommentTest() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.deleteComment(comment.getId());

        verify(commentRepository, times(1)).deleteById(comment.getId());
    }
}