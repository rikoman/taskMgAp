package com.example.backend.api.service;

import com.example.backend.api.DTO.TaskDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;
    private final ProjectService projectService;

    public Task createTask(TaskDTO dto){

        Category category = dto.getCategoryId() != null ? categoryService.readCategoryById(dto.getCategoryId()) : null;
        Project project = dto.getProjectId() != null ? projectService.readProjectById(dto.getProjectId()) : null;
        Task parentTask = dto.getParentId() != null ? readTaskById(dto.getParentId()) : null;

        Task task = Task.builder()
                .title(dto.getTitle())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .project(project)
                .category(category)
                .date(dto.getDate())
                .parent(parentTask)
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

    public Task readTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found"));
    }

    public List<Task> readAllTaskByCategoryId(Long id){
        return taskRepository.findByCategoryId(id);
    }

    public List<Task> readAllTaskByProjectId(Long id) { return taskRepository.findByProjectId(id);}

    public Task updateTask(Task task){
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
