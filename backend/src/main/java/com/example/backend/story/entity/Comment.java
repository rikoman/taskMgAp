package com.example.backend.story.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    private String content;
    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Task task;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;
}