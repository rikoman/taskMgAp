package com.example.backend.api.component;

import com.example.backend.story.DTO.PageDataDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MappingResponse<T> {

    public ResponseEntity<T> entity(T entity){
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public ResponseEntity<PageDataDTO<T>> listEntity(PageDataDTO<T> entity){
        return new ResponseEntity<>(entity,HttpStatus.OK);
    }
}