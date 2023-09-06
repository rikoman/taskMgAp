package com.example.backend.controller;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.entity.Task;
import com.example.backend.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping
    public ResponseEntity<Task> create (@RequestBody TaskDTO dto){
        return new ResponseEntity<>(taskService.create(dto), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Task>> readAll(){
        return new ResponseEntity<>(taskService.readAllTask(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task){
        return new ResponseEntity<>(taskService.update(task),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        taskService.delete(id);
        return HttpStatus.OK;
    }
}
