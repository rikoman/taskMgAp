package com.example.backend.api.controller;

import com.example.backend.api.DTO.CommentDTO;
import com.example.backend.api.service.CommentService;
import com.example.backend.story.entity.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService service;

    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO dto){
        return mappingResponseComment(service.createComment(dto));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> readAllComment(){
        return mappingResponseListComment(service.readAllComment());
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Comment>> readAllCommentByProjectId(@PathVariable Long id){
        return mappingResponseListComment(service.readAllCommentByProjectId(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Comment>> readAllCommentByCategoryId(@PathVariable Long id){
        return mappingResponseListComment(service.readAllCommentByCategoryId(id));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<Comment>> readAllCommentByTaskId(@PathVariable Long id){
        return mappingResponseListComment(service.readAllCommentByTaskId(id));
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment){
        return mappingResponseComment(service.updateComment(comment));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteComment(@PathVariable Long id){
        service.deleteComment(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Comment> mappingResponseComment(Comment comment){
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    private ResponseEntity<List<Comment>> mappingResponseListComment(List<Comment> comment){
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }
}