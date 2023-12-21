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

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updatePartInfo(@PathVariable Long id, @RequestBody CommentDTO dto,Authentication authentication){
        return mappingResponse.entity(service.updatePartInfoForComment(id, dto, authentication));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteComment(@PathVariable Long id, Authentication authentication){
        service.deleteComment(id, authentication);
        return HttpStatus.OK;
    }
}