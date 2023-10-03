package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class CommentDTO {
    @NotNull
    private String content;
    private Long taskId;
    private Long projectId;
    private Long categoryId;
}