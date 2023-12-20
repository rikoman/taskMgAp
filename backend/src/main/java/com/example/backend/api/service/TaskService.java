package com.example.backend.api.service;

import com.example.backend.api.component.CustomUserPrincipal;
import com.example.backend.story.DTO.TaskDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.enums.Priority;
import com.example.backend.story.enums.Status;
import com.example.backend.story.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;
    private final ProjectService projectService;
    private final UserService userService;
    private final CustomUserPrincipal customUserPrincipal;

    @CacheEvict(cacheNames = {"tasks", "tasksByStatusFalse", "tasksByProjectId", "taskByCategoryId" }, allEntries = true)
    public Task createTask(TaskDTO dto, Authentication authentication){
        if(dto.getTitle() == null || dto.getStatus() == null){
            throw new BadRequestException("Invalid request");
        }

        if(dto.getProjectId() == null && dto.getCategoryId() == null && dto.getParentId() == null){
            throw new BadRequestException("Invalid request");
        }

        if ((dto.getProjectId() != null && dto.getCategoryId() != null) || (dto.getProjectId() != null && dto.getParentId() != null) || (dto.getCategoryId() != null && dto.getParentId() != null)){
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
                .author(userService.readUserById(customUserPrincipal.getUserDetails(authentication).getId()))
                .build();

        return taskRepository.save(task);
    }

    @Cacheable(cacheNames = "tasks")
    public Page<Task> readAllTask(PageRequest pageRequest){
        return taskRepository.findAll(pageRequest);
    }

    @Cacheable(cacheNames = "tasksByStatusFalse")
    public Page<Task> readAllTaskByStatusFalse(PageRequest pageRequest){
        return taskRepository.findByStatusFalse(pageRequest);
    }

    @Cacheable(cacheNames = "task", key = "#id")
    public Task readTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("task with /%s/ id doesn' found",id)));
    }

    @Cacheable(cacheNames = "tasksByCategoryId", key = "#id")
    public Page<Task> readAllTaskByCategoryId(Long id, String status, String priority, PageRequest pageRequest){
        try{
            Status statusEnum = Status.valueOf(status.toUpperCase());
            Priority priorityEnum = Priority.valueOf(priority.toUpperCase());

            categoryService.readCategoryById(id);

            return taskRepository.findByCategoryIdAndStatusAndPriority(id,statusEnum,priorityEnum,pageRequest);
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Cacheable(cacheNames = "tasksByProjectId", key = "#id")
    public Page<Task> readAllTaskByProjectId(Long id,String status, String priority,PageRequest pageRequest) {
        try{
            Status statusEnum = Status.valueOf(status.toUpperCase());
            Priority priorityEnum = Priority.valueOf(priority.toUpperCase());

            projectService.readProjectById(id);

            return taskRepository.findByProjectIdAndStatusAndPriority(id,statusEnum,priorityEnum,pageRequest);
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

//    @Caching(evict = { @CacheEvict(cacheNames = "task", key = "#task.id"),
//                       @CacheEvict(cacheNames = {"tasks", "tasksByStatusFalse", "tasksByProjectId", "taskByCategoryId" }, allEntries = true)})
//    public Task updateTask(Task task){
//        if(task.getId() == null || task.getTitle() == null || task.getStatus() == null){
//            throw new BadRequestException("Invalid request");
//        }
//
//        if(task.getProject() == null && task.getCategory() == null && task.getParent() == null){
//            throw new BadRequestException("Invalid request");
//        }
//
//        if ((task.getProject() != null && task.getCategory() != null) || (task.getProject() != null && task.getParent() != null) || (task.getCategory() != null && task.getParent() != null)){
//            throw new BadRequestException("Invalid request");
//        }
//
//        return taskRepository.save(task);
//    }

    @Caching(evict = { @CacheEvict(cacheNames = "task", key = "#task.id"),
                       @CacheEvict(cacheNames = {"tasks", "tasksByStatusFalse", "tasksByProjectId", "taskByCategoryId" }, allEntries = true)})
    public Task updatePartInfoForTask(Long id, TaskDTO dto){
        Task existTask = readTaskById(id);

        if(dto.getTitle() != null){
            existTask.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null){
            existTask.setDescription(dto.getDescription());
        }

        if(dto.getPriority() != null){
            existTask.setPriority(dto.getPriority());
        }

        if(dto.getStatus() != null){
            existTask.setStatus(dto.getStatus());
        }

        if(dto.getProjectId() != null){
            Project existProject = projectService.readProjectById(dto.getProjectId());
            existTask.setProject(existProject);
        }

        if(dto.getCategoryId() != null){
            Category existCategory = categoryService.readCategoryById(dto.getCategoryId());
            existTask.setCategory(existCategory);
        }

        if(dto.getParentId() != null){
            Task existParentTask = readTaskById(dto.getParentId());
            existTask.setParent(existParentTask);
        }

        return taskRepository.save(existTask);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "task", key = "#id"),
                       @CacheEvict(cacheNames = {"tasks", "tasksByStatusFalse", "tasksByProjectId", "taskByCategoryId" }, allEntries = true)})
    public void deleteTask(Long id){
        readTaskById(id);
        taskRepository.deleteById(id);
    }
}
