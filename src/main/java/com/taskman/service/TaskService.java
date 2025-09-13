package com.taskman.service;

import com.taskman.model.Task;
import com.taskman.model.TaskPriority;
import com.taskman.repository.ProjectRepository;
import com.taskman.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Task business logic
 */
@ApplicationScoped
public class TaskService {

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private ProjectRepository projectRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getTasksByProjectIdAndPriority(Long projectId, TaskPriority priority) {
        return taskRepository.findByProjectIdAndPriority(projectId, priority);
    }

    public List<Task> getTasksByProjectIdAndDone(Long projectId, Boolean done) {
        return taskRepository.findByProjectIdAndDone(projectId, done);
    }

    public List<Task> getOverdueTasks(Long projectId) {
        return taskRepository.findOverdueTasks(projectId);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        validateTask(task);
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        validateTask(task);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        taskRepository.deleteById(id);
    }

    public void deleteTasksByProjectId(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        taskRepository.deleteByProjectId(projectId);
    }

    public Task toggleTaskCompletion(Long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setDone(!task.isDone());
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task not found with ID: " + taskId);
    }

    public boolean isTaskOverdue(Task task) {
        if (task == null || task.isDone()) {
            return false;
        }
        return task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now());
    }

    public long getTaskCountByProjectId(Long projectId) {
        return taskRepository.countByProjectId(projectId);
    }

    public long getTaskCountByProjectIdAndDone(Long projectId, boolean done) {
        return taskRepository.countByProjectIdAndDone(projectId, done);
    }

    public long getTotalTaskCount() {
        return taskRepository.count();
    }

    private void validateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title is required");
        }
        
        if (task.getProjectId() == null) {
            throw new IllegalArgumentException("Project ID is required");
        }
        
        // Validate that the project exists
        if (!projectRepository.findById(task.getProjectId()).isPresent()) {
            throw new IllegalArgumentException("Project not found with ID: " + task.getProjectId());
        }
        
        // Validate due date is not in the past
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
    }
}
