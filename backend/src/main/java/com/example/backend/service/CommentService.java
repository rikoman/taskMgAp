package com.example.backend.service;

import com.example.backend.DTO.CommentDTO;
import com.example.backend.entity.Comment;
import com.example.backend.repository.CommentRepository;
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
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .task(taskService.readTaskById(dto.getTaskId()))
                .category(categoryService.readCategoryById(dto.getCategoryId()))
                .project(projectService.readProjectById(dto.getProjectId()))
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