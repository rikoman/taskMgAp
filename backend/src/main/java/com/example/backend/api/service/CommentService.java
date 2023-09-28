package com.example.backend.api.service;

import com.example.backend.api.DTO.CommentDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Comment;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final ProjectService projectService;
    private final CategoryService categoryService;
    private final TaskService taskService;

    public Comment createComment(CommentDTO dto){
        Task task = dto.getTaskId() != null ? taskService.readTaskById(dto.getTaskId()) : null;
        Category category = dto.getCategoryId() != null ? categoryService.readCategoryById(dto.getCategoryId()) : null;
        Project project = dto.getProjectId() != null ? projectService.readProjectById(dto.getProjectId()) : null;

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .task(task)
                .category(category)
                .project(project)
                .build();
        return repository.save(comment);
    }

    public List<Comment> readAllComment(){
        return repository.findAll();
    }

    public List<Comment> readAllCommentByProjectId(Long id){
        return repository.findByProjectId(id);
    }

    public List<Comment> readAllCommentByCategoryId(Long id){
        return repository.findByCategoryId(id);
    }

    public List<Comment> readAllCommentByTaskId(Long id){
        return repository.findByTaskId(id);
    }

    public Comment updateComment(Comment comment){
        return repository.save(comment);
    }

    public void deleteComment(Long id){
        repository.deleteById(id);
    }
}