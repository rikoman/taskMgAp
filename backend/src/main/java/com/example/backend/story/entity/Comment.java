package com.example.backend.story.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "content not must blank")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id")
    @JsonIdentityReference(alwaysAsId = true)
    @NotNull(message = "task not must empty")
    private Task task;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "datePublication not must empty")
    private LocalDateTime datePublication;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull(message = "author not must empty")
    @JsonIdentityReference(alwaysAsId = true)
    private User author;
}