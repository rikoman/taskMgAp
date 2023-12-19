package com.example.backend.api.component;

import com.example.backend.story.DTO.PageDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageData<T> {

    public PageDataDTO<T> pageDataDTO(Page<T> entityPage){
        return new PageDataDTO<>(entityPage.getContent(),entityPage.getTotalElements());
    }

}