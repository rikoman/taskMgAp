package com.example.backend.api.service;

import com.example.backend.api.component.CustomUserPrincipal;
import com.example.backend.story.DTO.CommentDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Comment;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.CommentRepository;
import com.example.backend.story.repository.TaskRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
//    @CacheEvict(cacheNames = {"comments", "commentsByTaskId"}, allEntries = true)
    public Comment createComment(CommentDTO dto, Authentication authentication){
        Task task = taskService.readTaskById(dto.getTaskId(),authentication);

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .task(task)
                .datePublication(LocalDateTime.now())
                .author(userService.readUserById(getUserIdPrincipal(authentication)))
                .build();

        Comment savedComment = commentRepository.save(comment);

        task.getComments().add(savedComment);

        taskRepository.save(task);

        return savedComment;
    }

    @Transactional
//    @Cacheable(cacheNames = "comments")
    public Page<Comment> readAllComment(PageRequest pageRequest){
        return commentRepository.findAll(pageRequest);
    }

    @Transactional
//    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#comment.id"),
//            @CacheEvict(cacheNames = {"comments", "commentsByTaskId"}, allEntries = true) })
    public Comment updatePartInfoForComment(Long id,CommentDTO dto,Authentication authentication){
        Comment existComment = findByIdComment(id,authentication);

        if(existComment.getAuthor().getId().equals(getUserIdPrincipal(authentication))){
            if(dto.getContent() != null){
                existComment.setContent(dto.getContent());
            }
        }else {
            throw new BadRequestException("Только автор может редактировать комментарий");
        }
        return commentRepository.save(existComment);
    }

    @Transactional
//    @Caching(evict = { @CacheEvict(cacheNames = "comment", key = "#id"),
//                       @CacheEvict(cacheNames = {"comments", "commentsByTaskId"}, allEntries = true) })
    public void deleteComment(Long id, Authentication authentication){
        Comment existComment = findByIdComment(id,authentication);
        if(existComment.getAuthor().getId().equals(getUserIdPrincipal(authentication))){
            commentRepository.deleteById(id);
        }
        else {
            throw new BadRequestException("Только автор может удалять комментарий");
        }
    }

    private Comment findByIdComment(Long id, Authentication authentication){
        Comment existComment = commentRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("comment with /%s/ id doesn' found",id)));
        taskService.readTaskById(existComment.getTask().getId(),authentication);
        return existComment;
    }

    private Long getUserIdPrincipal(Authentication authentication){
        return customUserPrincipal.getUserDetails(authentication).getId();
    }
}