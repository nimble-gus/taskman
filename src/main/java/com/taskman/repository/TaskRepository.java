package com.taskman.repository;

import com.taskman.model.Task;
import com.taskman.model.TaskPriority;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory repository for Task entities
 */
@ApplicationScoped
public class TaskRepository {
    
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Seed data for projects 1, 2, 3, 4
        createTask(1L, "Diseñar base de datos", TaskPriority.HIGH, 
                  LocalDate.now().plusDays(5), "Crear el modelo de datos para inventarios");
        
        createTask(1L, "Implementar API REST", TaskPriority.HIGH, 
                  LocalDate.now().plusDays(10), "Desarrollar endpoints para CRUD de productos");
        
        createTask(1L, "Crear interfaz de usuario", TaskPriority.MEDIUM, 
                  LocalDate.now().plusDays(15), "Diseñar y desarrollar la UI con React");
        
        createTask(1L, "Configurar servidor de producción", TaskPriority.LOW, 
                  LocalDate.now().plusDays(20), "Preparar ambiente de producción");
        
        createTask(2L, "Evaluar proveedores cloud", TaskPriority.HIGH, 
                  LocalDate.now().minusDays(2), "Comparar AWS, Azure y Google Cloud");
        
        createTask(2L, "Crear plan de migración", TaskPriority.MEDIUM, 
                  LocalDate.now().plusDays(7), "Documentar pasos para migración");
        
        createTask(3L, "Diseñar wireframes", TaskPriority.MEDIUM, 
                  LocalDate.now().plusDays(3), "Crear mockups del portal");
        
        createTask(3L, "Implementar autenticación", TaskPriority.HIGH, 
                  LocalDate.now().plusDays(8), "Sistema de login y registro");
        
        createTask(3L, "Desarrollar dashboard", TaskPriority.MEDIUM, 
                  LocalDate.now().plusDays(12), "Panel principal del portal");
        
        createTask(4L, "Configurar automatización", TaskPriority.LOW, 
                  LocalDate.now().minusDays(5), "Setup de jobs automáticos");
        
        createTask(4L, "Crear plantillas de reportes", TaskPriority.MEDIUM, 
                  LocalDate.now().minusDays(1), "Diseñar formatos de reportes");
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> findByProjectId(Long projectId) {
        if (projectId == null) {
            return findAll();
        }
        return tasks.values().stream()
                .filter(task -> task.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    public List<Task> findByProjectIdAndPriority(Long projectId, TaskPriority priority) {
        List<Task> projectTasks = findByProjectId(projectId);
        if (priority == null) {
            return projectTasks;
        }
        return projectTasks.stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> findByProjectIdAndDone(Long projectId, Boolean done) {
        List<Task> projectTasks = findByProjectId(projectId);
        if (done == null) {
            return projectTasks;
        }
        return projectTasks.stream()
                .filter(task -> task.isDone() == done)
                .collect(Collectors.toList());
    }

    public List<Task> findOverdueTasks(Long projectId) {
        LocalDate today = LocalDate.now();
        return findByProjectId(projectId).stream()
                .filter(task -> !task.isDone() && task.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    public long countByProjectId(Long projectId) {
        return findByProjectId(projectId).size();
    }

    public long countByProjectIdAndDone(Long projectId, boolean done) {
        return findByProjectIdAndDone(projectId, done).size();
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public void deleteById(Long id) {
        tasks.remove(id);
    }

    public void deleteByProjectId(Long projectId) {
        List<Long> taskIdsToDelete = findByProjectId(projectId).stream()
                .map(Task::getId)
                .collect(Collectors.toList());
        taskIdsToDelete.forEach(tasks::remove);
    }

    public long count() {
        return tasks.size();
    }

    private void createTask(Long projectId, String title, TaskPriority priority, 
                           LocalDate dueDate, String notes) {
        Task task = new Task();
        task.setId(idGenerator.getAndIncrement());
        task.setProjectId(projectId);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setNotes(notes);
        task.setDone(new Random().nextBoolean()); // Random completion status for seed data
        tasks.put(task.getId(), task);
    }
}
