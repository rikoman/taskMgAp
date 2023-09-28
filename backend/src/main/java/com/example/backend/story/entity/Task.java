package com.example.backend.story.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String date;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;
}