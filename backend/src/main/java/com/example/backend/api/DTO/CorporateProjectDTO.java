package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CorporateProjectDTO {
    private String title;
    private String description;
    private List<Long> users;
}