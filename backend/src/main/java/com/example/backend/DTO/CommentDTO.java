package com.example.backend.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    private String content;
    private Long taskId;
    private Long projectId;
    private Long categoryId;
}