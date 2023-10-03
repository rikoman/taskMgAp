package com.example.backend.api.service;

import com.example.backend.api.DTO.CommentDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
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
        if(dto.getContent()==null || dto.getCategoryId() == null && dto.getProjectId() == null && dto.getTaskId() == null){
            throw new BadRequestException("Invalid request");
        }

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
        projectService.readProjectById(id);
        return repository.findByProjectId(id);
    }

    public List<Comment> readAllCommentByCategoryId(Long id){
        categoryService.readCategoryById(id);
        return repository.findByCategoryId(id);
    }

    public List<Comment> readAllCommentByTaskId(Long id){
        taskService.readTaskById(id);
        return repository.findByTaskId(id);
    }

    public Comment updateComment(Comment comment){
        if(comment.getId() == null || comment.getContent() == null || comment.getCategory() == null && comment.getProject() == null && comment.getTask() == null) {
            throw new BadRequestException("Invalid request");
        }
        return repository.save(comment);
    }

    public void deleteComment(Long id){
        repository.findById(id).orElseThrow(()-> new NotFoundException(String.format("comment with %s id doesn' found",id)));
        repository.deleteById(id);
    }
}