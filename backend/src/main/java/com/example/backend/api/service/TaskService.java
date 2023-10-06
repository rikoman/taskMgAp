package com.example.backend.api.service;

import com.example.backend.api.DTO.TaskDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
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
        if(dto.getTitle()==null || dto.getStatus()==null || dto.getCategoryId() == null && dto.getParentId() == null && dto.getProjectId() == null){
            throw new BadRequestException("Invalid request");
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
                .date(dto.getDate())
                .parent(parentTask)
                .build();
        return taskRepository.save(task);
    }

    public List<Task> readAllTask(){
        return taskRepository.findAll();
    }

    public List<Task> readAllTaskByStatusFalse(){return taskRepository.findByStatusFalse();}

    public Task readTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("task with %s id doesn' found",id)));
    }

    public List<Task> readAllTaskByCategoryId(Long id){
        categoryService.readCategoryById(id);
        return sortingListTask(taskRepository.findByCategoryIdAndStatusTrue(id));
    }

    public List<Task> readAllTaskByProjectId(Long id) {
        projectService.readProjectById(id);
        return sortingListTask(taskRepository.findByProjectIdAndStatusTrue(id));
    }

    public Task updateTask(Task task){
        if(task.getId() == null || task.getTitle() == null || task.getStatus() == null || task.getCategory() == null && task.getParent() == null && task.getProject() == null) {
            throw new BadRequestException("Invalid request");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        readTaskById(id);
        taskRepository.deleteById(id);
    }

    private List<Task> sortingListTask(List<Task> tasks){
        tasks.sort(Comparator.comparing(Task::getPriority));
        return tasks;
    }
}
