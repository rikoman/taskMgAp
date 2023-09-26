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
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) throws ParseException {
        if(dto.getDate()== null){
            dto.setDate(String.valueOf(LocalDate.now()));
        }
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAllTask(){
        return new ResponseEntity<>(taskService.readAllTask(),HttpStatus.OK);
    }

    @GetMapping("/true")
    public ResponseEntity<List<Task>> readAllTaskByStatusTrue(){
        return new ResponseEntity<>(taskService.readAllTaskByStatusTrue(),HttpStatus.OK);
    }

    @GetMapping("/priority/{id}")
    public ResponseEntity<List<Task>> readAllTaskByStatusTrueAndByPriority(@PathVariable Integer id){
        return new ResponseEntity<>(taskService.readAllTaskByStatusTrueAndByPriority(id),HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Task>> readAllSortTaskByStatusTrueAndByPriority(){
        return new ResponseEntity<>(taskService.readAllSortTaskByStatusTrueAndByPriority(),HttpStatus.OK);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Task>> readTasksToday(){

        return new ResponseEntity<>(taskService.readTasksByDateTodayAndStatusTrue(), HttpStatus.OK);
    }
    @GetMapping("/{date}")
    public ResponseEntity<List<Task>> readTasksByDate(@PathVariable String date){
        return new ResponseEntity<>(taskService.readTasksByDateAdnStatusTrue(date), HttpStatus.OK);
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