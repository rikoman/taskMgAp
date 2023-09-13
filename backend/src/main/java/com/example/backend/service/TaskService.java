package com.example.backend.service;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.entity.Task;
import com.example.backend.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(TaskDTO dto){
        Task task = Task.builder()
                .title(dto.getTitle())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .build();
        return taskRepository.save(task);
    }

    public List<Task> readAllTask(){
        return taskRepository.findAll();
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
