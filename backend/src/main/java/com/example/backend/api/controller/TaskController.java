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

    private final MappingResponse<Task> mappingResponse;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) throws ParseException {
        return mappingResponse.entity(taskService.createTask(dto));
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAllTask(){
        return mappingResponse.listEntity(taskService.readAllTask());
    }

    @GetMapping("/false")
    public ResponseEntity<List<Task>> readAllTaskByStatusFalse(){
        return mappingResponse.listEntity(taskService.readAllTaskByStatusFalse());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Task> readTaskById(@PathVariable Long id){
        return mappingResponse.entity(taskService.readTaskById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Task>> readAllTaskByCategoryId(@PathVariable Long id){
        return mappingResponse.listEntity(taskService.readAllTaskByCategoryId(id));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Task>> readAlTaskByProjectId(@PathVariable Long id){
        return mappingResponse.listEntity(taskService.readAllTaskByProjectId(id));
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task){
        return mappingResponse.entity(taskService.updateTask(task));
    }

    @PatchMapping
    public ResponseEntity<Task> updatePartInfoForTask(@RequestBody Task task){
        return mappingResponse.entity(taskService.updatePartInfoForTask(task));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return HttpStatus.OK;
    }
}