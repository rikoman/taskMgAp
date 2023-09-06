package com.example.backend.service;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.entity.Task;
import com.example.backend.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(TaskDTO dto){
        Task task = Task.builder()
                .title(dto.getTitle())
                .activity(dto.getActivity())
                .build();
        return taskRepository.save(task);
    }

    public List<Task> readAllTask(){
        return taskRepository.findAll();
    }

    public Task update(Task task){
        return taskRepository.save(task);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }
}
