package com.example.backend.api.controller;

import com.example.backend.story.DTO.TaskDTO;
import com.example.backend.story.entity.Task;
import com.example.backend.api.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) throws ParseException {
        return mappingResponseTask(taskService.createTask(dto));
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAllTask(){
        return mappingResponseListTask(taskService.readAllTask());
    }

    @GetMapping("/false")
    public ResponseEntity<List<Task>> readAllTaskByStatusFalse(){
        return mappingResponseListTask(taskService.readAllTaskByStatusFalse());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Task> readTaskById(@PathVariable Long id){
        return mappingResponseTask(taskService.readTaskById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Task>> readAllTaskByCategoryId(@PathVariable Long id){
        return mappingResponseListTask(taskService.readAllTaskByCategoryId(id));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Task>> readAlTaskByProjectId(@PathVariable Long id){
        return mappingResponseListTask(taskService.readAllTaskByProjectId(id));
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        return mappingResponseTask(taskService.updateTask(task));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Task> mappingResponseTask(Task task){
        return new ResponseEntity<>(task,HttpStatus.OK);
    }

    private ResponseEntity<List<Task>> mappingResponseListTask(List<Task> task){
        return new ResponseEntity<>(task,HttpStatus.OK);
    }
}