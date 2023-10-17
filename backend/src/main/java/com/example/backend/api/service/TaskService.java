package com.example.backend.api.service;

import com.example.backend.story.DTO.TaskDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @CacheEvict(cacheNames = "tasks", allEntries = true)
    public Task createTask(TaskDTO dto){
        if(dto.getTitle()==null || dto.getStatus()==null || dto.getCategoryId() == null && dto.getParentId() == null && dto.getProjectId() == null){
            throw new BadRequestException("Invalid request");
        }

        if(dto.getDateCreate()== null){
            dto.setDateCreate(LocalDate.now());
        }

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
                .dateCreate(dto.getDateCreate())
                .parent(parentTask)
                .build();
        return taskRepository.save(task);
    }

    @Cacheable(cacheNames = "tasks")
    public List<Task> readAllTask(){
        return taskRepository.findAll();
    }

    @Cacheable(cacheNames = "tasksByStatusFalse")
    public List<Task> readAllTaskByStatusFalse(){return taskRepository.findByStatusFalse();}

    @Cacheable(cacheNames = "task", key = "#id")
    public Task readTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("task with %s id doesn' found",id)));
    }

    @Cacheable(cacheNames = "tasksByCategoryId", key = "#id")
    public List<Task> readAllTaskByCategoryId(Long id){
        categoryService.readCategoryById(id);
        return sortingListTask(taskRepository.findByCategoryIdAndStatusTrue(id));
    }

    @Cacheable(cacheNames = "tasksByProjectId", key = "#id")
    public List<Task> readAllTaskByProjectId(Long id) {
        projectService.readProjectById(id);
        return sortingListTask(taskRepository.findByProjectIdAndStatusTrue(id));
    }

    @CacheEvict(cacheNames = "tasks", allEntries = true)
    public Task updateTask(Task task){
        if(task.getId() == null || task.getTitle() == null || task.getStatus() == null || task.getCategory() == null && task.getParent() == null && task.getProject() == null) {
            throw new BadRequestException("Invalid request");
        }
        return taskRepository.save(task);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "task", key = "#id"),
                       @CacheEvict(cacheNames = {"tasks", "tasksByStatusFalse", "tasksByProjectId", "taskByCategoryId" }, allEntries = true)})
    public void deleteTask(Long id){
        readTaskById(id);
        taskRepository.deleteById(id);
    }

    private List<Task> sortingListTask(List<Task> tasks){
        tasks.sort(Comparator.comparing(Task::getPriority));
        return tasks;
    }
}
