package com.example.backend.controller;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.entity.Task;
import com.example.backend.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) throws ParseException {
        if(dto.getDate()== null){
            dto.setDate(String.valueOf(LocalDate.now()));
        }
        return mappingResponseTask(taskService.createTask(dto));
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAllTask(){
        return mappingResponseListTask(taskService.readAllTask());
    }

    @GetMapping("/true")
    public ResponseEntity<List<Task>> readAllTaskByStatusTrue(){
        return mappingResponseListTask(taskService.readAllTaskByStatusTrue());
    }

    @GetMapping("/priority/{id}")
    public ResponseEntity<List<Task>> readAllTaskByStatusTrueAndByPriority(@PathVariable Integer id){
        return mappingResponseListTask(taskService.readAllTaskByStatusTrueAndByPriority(id));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Task>> readAllSortTaskByStatusTrueAndByPriority(){
        return mappingResponseListTask(taskService.readAllSortTaskByStatusTrueAndByPriority());
    }

    @GetMapping("/today")
    public ResponseEntity<List<Task>> readTasksToday(){
        return mappingResponseListTask(taskService.readTasksByDateTodayAndStatusTrue());
    }
    @GetMapping("/{date}")
    public ResponseEntity<List<Task>> readTasksByDate(@PathVariable String date){
        return mappingResponseListTask(taskService.readTasksByDateAdnStatusTrue(date));
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Task> readTaskById(@PathVariable Long id){
        return mappingResponseTask(taskService.readTaskById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Task>> readProductByCategoryId(@PathVariable Long id){
        return mappingResponseListTask(taskService.readProductByCategoryId(id));
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