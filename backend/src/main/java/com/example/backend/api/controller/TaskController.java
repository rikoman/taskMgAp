package com.example.backend.api.controller;

import com.example.backend.api.component.MappingResponse;
import com.example.backend.api.component.PageData;
import com.example.backend.story.DTO.PageDataDTO;
import com.example.backend.story.DTO.TaskDTO;
import com.example.backend.story.entity.Task;
import com.example.backend.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final MappingResponse<Task> mappingResponse;
    private final PageData<Task> pageData;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) throws ParseException {
        return mappingResponse.entity(taskService.createTask(dto));
    }

    @GetMapping
    public ResponseEntity<PageDataDTO<Task>> readAllTask(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(taskService.readAllTask(PageRequest.of(page, size))));
    }

    @GetMapping("/false")
    public ResponseEntity<PageDataDTO<Task>> readAllTaskByStatusFalse(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(taskService.readAllTaskByStatusFalse(PageRequest.of(page, size))));
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Task> readTaskById(@PathVariable Long id){
        return mappingResponse.entity(taskService.readTaskById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<PageDataDTO<Task>> readAllTaskByCategoryId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(taskService.readAllTaskByCategoryId(id,PageRequest.of(page,size))));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<PageDataDTO<Task>> readAlTaskByProjectId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(taskService.readAllTaskByProjectId(id,PageRequest.of(page, size))));
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