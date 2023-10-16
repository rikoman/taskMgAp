package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String content;
    private Long taskId;
    private Long projectId;
    private Long categoryId;
}