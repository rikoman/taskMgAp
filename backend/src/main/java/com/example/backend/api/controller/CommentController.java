package com.example.backend.api.controller;

import com.example.backend.api.component.MappingResponse;
import com.example.backend.api.component.PageData;
import com.example.backend.story.DTO.CommentDTO;
import com.example.backend.api.service.CommentService;
import com.example.backend.story.DTO.PageDataDTO;
import com.example.backend.story.entity.Comment;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService service;
    private final MappingResponse<Comment> mappingResponse;
    private final PageData<Comment> pageData;

    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO dto, Authentication authentication){
        return mappingResponse.entity(service.createComment(dto,authentication));
    }

    @GetMapping
    public ResponseEntity<PageDataDTO<Comment>> readAllComment(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(service.readAllComment(PageRequest.of(page, size))));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<PageDataDTO<Comment>> readAllCommentByProjectId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(service.readAllCommentByProjectId(id,PageRequest.of(page,size))));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<PageDataDTO<Comment>> readAllCommentByCategoryId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(service.readAllCommentByCategoryId(id,PageRequest.of(page,size))));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<PageDataDTO<Comment>> readAllCommentByTaskId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(service.readAllCommentByTaskId(id, PageRequest.of(page,size))));
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