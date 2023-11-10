package com.example.backend.api.service;

import com.example.backend.story.DTO.CommentDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Comment;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProjectService projectService;
    private final CategoryService categoryService;
    private final TaskService taskService;

    @CacheEvict(cacheNames = {"comments", "commentsByProjectId", "commentsByCategoryId", "commentsByTaskId"}, allEntries = true)
    public Comment createComment(CommentDTO dto){
        if(dto.getContent()==null){
            throw new BadRequestException("Invalid request");
        }

        if(dto.getProjectId() == null && dto.getCategoryId() == null && dto.getTaskId() == null){
            throw new BadRequestException("Invalid request");
        }

        if ((dto.getProjectId() != null && dto.getCategoryId() != null) || (dto.getProjectId() != null && dto.getTaskId() != null) || (dto.getCategoryId() != null && dto.getTaskId() != null)){
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
                .datePublication(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    @Cacheable(cacheNames = "comments")
    public List<Comment> readAllComment(){
        return commentRepository.findAll();
    }

    @Cacheable(cacheNames = "commentsByProjectId", key = "#id")
    public List<Comment> readAllCommentByProjectId(Long id){
        projectService.readProjectById(id);
        return commentRepository.findByProjectId(id);
    }

    @Cacheable(cacheNames = "commentsByCategoryId", key = "#id")
    public List<Comment> readAllCommentByCategoryId(Long id){
        categoryService.readCategoryById(id);
        return commentRepository.findByCategoryId(id);
    }

    @Cacheable(cacheNames = "commentsByTaskId", key = "#id")
    public List<Comment> readAllCommentByTaskId(Long id){
        taskService.readTaskById(id);
        return commentRepository.findByTaskId(id);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#comment.id"),
                       @CacheEvict(cacheNames = {"comments", "commentsByProjectId", "commentsByCategoryId", "commentsByTaskId"}, allEntries = true) })
    public Comment updateComment(Comment comment){
        if(comment.getId() == null || comment.getContent()==null){
            throw new BadRequestException("Invalid request");
        }

        if(comment.getProject() == null && comment.getCategory() == null && comment.getTask() == null){
            throw new BadRequestException("Invalid request");
        }

        if ((comment.getProject() != null && comment.getCategory() != null) || (comment.getProject() != null && comment.getTask() != null) || (comment.getCategory() != null && comment.getTask() != null)){
            throw new BadRequestException("Invalid request");
        }

        return commentRepository.save(comment);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#comment.id"),
            @CacheEvict(cacheNames = {"comments", "commentsByProjectId", "commentsByCategoryId", "commentsByTaskId"}, allEntries = true) })
    public Comment updatePartInfoForComment(Comment comment){
        Comment existComment = commentRepository.findById(comment.getId()).orElseThrow(()-> new NotFoundException(String.format("comment with /%s/ id doesn' found",comment.getId())));

        if(comment.getContent() != null){
            existComment.setContent(comment.getContent());
        }
        return commentRepository.save(existComment);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#id"),
                       @CacheEvict(cacheNames = {"comments", "commentsByProjectId", "commentsByCategoryId", "commentsByTaskId"}, allEntries = true) })
    public void deleteComment(Long id){
        commentRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("comment with /%s/ id doesn' found",id)));
        commentRepository.deleteById(id);
    }
}