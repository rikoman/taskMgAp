package com.example.backend.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MappingResponse<T> {

    private T entity;

    public ResponseEntity<T> entity(T entity){
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public ResponseEntity<List<T>> listEntity(List<T> entity){
        return new ResponseEntity<>(entity,HttpStatus.OK);
    }
}