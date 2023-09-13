package com.example.backend.controller;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.entity.Task;
import com.example.backend.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto){
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Task>> readAllTask(){
        return new ResponseEntity<>(taskService.readAllTask(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.updateTask(task),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public HttpStatus deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return HttpStatus.OK;
    }
}
