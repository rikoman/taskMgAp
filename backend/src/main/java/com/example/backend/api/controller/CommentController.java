package com.example.backend.api.controller;

import com.example.backend.story.DTO.CommentDTO;
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

    private final CommentService service;

    private final MappingResponse<Comment> mappingResponse;

    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO dto){
        return mappingResponse.entity(service.createComment(dto));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> readAllComment(){
        return mappingResponse.listEntity(service.readAllComment());
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Comment>> readAllCommentByProjectId(@PathVariable Long id){
        return mappingResponse.listEntity(service.readAllCommentByProjectId(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Comment>> readAllCommentByCategoryId(@PathVariable Long id){
        return mappingResponse.listEntity(service.readAllCommentByCategoryId(id));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<Comment>> readAllCommentByTaskId(@PathVariable Long id){
        return mappingResponse.listEntity(service.readAllCommentByTaskId(id));
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment){
        return mappingResponse.entity(service.updateComment(comment));
    }

    @PatchMapping
    public ResponseEntity<Comment> updatePartInfo(@RequestBody Comment comment){
        return mappingResponse.entity(service.updatePartInfoForComment(comment));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteComment(@PathVariable Long id){
        service.deleteComment(id);
        return HttpStatus.OK;
    }
}