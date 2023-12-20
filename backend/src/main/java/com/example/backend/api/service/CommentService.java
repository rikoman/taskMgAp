package com.example.backend.api.service;

import com.example.backend.api.component.CustomUserPrincipal;
import com.example.backend.story.DTO.CommentDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Comment;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.CommentRepository;
import com.example.backend.story.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CustomUserPrincipal customUserPrincipal;

    @CacheEvict(cacheNames = {"comments", "commentsByTaskId"}, allEntries = true)
    public Comment createComment(CommentDTO dto, Authentication authentication){
        Task task = taskService.readTaskById(dto.getTaskId());

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .task(task)
                .datePublication(LocalDateTime.now())
                .author(userService.readUserById(customUserPrincipal.getUserDetails(authentication).getId()))
                .build();

        Comment savedComment = commentRepository.save(comment);

        task.getComments().add(savedComment);

        taskRepository.save(task);

        return savedComment;
    }

    @Cacheable(cacheNames = "comments")
    public Page<Comment> readAllComment(PageRequest pageRequest){
        return commentRepository.findAll(pageRequest);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#comment.id"),
            @CacheEvict(cacheNames = {"comments", "commentsByTaskId"}, allEntries = true) })
    public Comment updatePartInfoForComment(Long id,CommentDTO dto){
        Comment existComment = findByIdComment(id);

        if(dto.getContent() != null){
            existComment.setContent(dto.getContent());
        }
        return commentRepository.save(existComment);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#id"),
                       @CacheEvict(cacheNames = {"comments", "commentsByTaskId"}, allEntries = true) })
    public void deleteComment(Long id){
        findByIdComment(id);
        commentRepository.deleteById(id);
    }

    private Comment findByIdComment(Long id){
        return commentRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("comment with /%s/ id doesn' found",id)));
    }
}