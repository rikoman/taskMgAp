package com.example.backend.service;

import com.example.backend.DTO.TaskDTO;
import com.example.backend.entity.Task;
import com.example.backend.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
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
                .user(dto.getUser())
                .date(dto.getDate())
                .build();
        return taskRepository.save(task);
    }

    public List<Task> readAllTask(){
        return taskRepository.findAll();
    }

    public List<Task> readAllTaskByStatusTrue(){return taskRepository.findByStatusTrue();}

    public List<Task> readAllTaskByStatusTrueAndByPriority(Integer id){
        return taskRepository.findByStatusTrueAndPriority(id);
    }

    public List<Task> readAllSortTaskByStatusTrueAndByPriority(){
        List<Task> sortTasks = taskRepository.findByStatusTrue();
        sortTasks.sort(Comparator.comparing(Task::getPriority));
        return sortTasks;
    }

    public List<Task> readTasksByDateTodayAndStatusTrue(){
        return taskRepository.findByDateAndStatusTrue(String.valueOf(LocalDate.now()));
    }
    public List<Task> readTasksByDateAdnStatusTrue(String date){
        return taskRepository.findByDateAndStatusTrue(date);
    }

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
