package com.taskman.repository;

import com.taskman.model.Project;
import com.taskman.model.ProjectStatus;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory repository for Project entities
 */
@ApplicationScoped
public class ProjectRepository {
    
    private final Map<Long, Project> projects = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Seed data
        createProject("Sistema de Gestión de Inventarios", "Juan Pérez", 
                     "Desarrollo de un sistema completo para la gestión de inventarios de la empresa", 
                     ProjectStatus.ACTIVE);
        
        createProject("Migración a la Nube", "María García", 
                     "Migración de sistemas legacy a infraestructura cloud", 
                     ProjectStatus.ON_HOLD);
        
        createProject("Portal de Clientes", "Carlos López", 
                     "Desarrollo de portal web para clientes con funcionalidades de autogestión", 
                     ProjectStatus.ACTIVE);
        
        createProject("Automatización de Reportes", "Ana Martínez", 
                     "Sistema automatizado para generación de reportes financieros", 
                     ProjectStatus.DONE);
    }

    public List<Project> findAll() {
        return new ArrayList<>(projects.values());
    }

    public Optional<Project> findById(Long id) {
        return Optional.ofNullable(projects.get(id));
    }

    public List<Project> findByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        return projects.values().stream()
                .filter(project -> project.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Project> findByOwnerContaining(String owner) {
        if (owner == null || owner.trim().isEmpty()) {
            return findAll();
        }
        return projects.values().stream()
                .filter(project -> project.getOwner().toLowerCase().contains(owner.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Project> findByStatus(ProjectStatus status) {
        if (status == null) {
            return findAll();
        }
        return projects.values().stream()
                .filter(project -> project.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Project> findByNameOrOwnerContaining(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        String lowerSearchTerm = searchTerm.toLowerCase();
        return projects.values().stream()
                .filter(project -> 
                    project.getName().toLowerCase().contains(lowerSearchTerm) ||
                    project.getOwner().toLowerCase().contains(lowerSearchTerm))
                .collect(Collectors.toList());
    }

    public List<Project> findByNameOrOwnerContainingAndStatus(String searchTerm, ProjectStatus status) {
        List<Project> filteredByNameOrOwner = findByNameOrOwnerContaining(searchTerm);
        if (status == null) {
            return filteredByNameOrOwner;
        }
        return filteredByNameOrOwner.stream()
                .filter(project -> project.getStatus() == status)
                .collect(Collectors.toList());
    }

    public boolean existsByName(String name) {
        return projects.values().stream()
                .anyMatch(project -> project.getName().equals(name));
    }

    public boolean existsByNameAndIdNot(String name, Long id) {
        return projects.values().stream()
                .anyMatch(project -> project.getName().equals(name) && !project.getId().equals(id));
    }

    public Project save(Project project) {
        if (project.getId() == null) {
            project.setId(idGenerator.getAndIncrement());
            project.setCreatedAt(LocalDateTime.now());
        }
        projects.put(project.getId(), project);
        return project;
    }

    public void deleteById(Long id) {
        projects.remove(id);
    }

    public long count() {
        return projects.size();
    }

    private void createProject(String name, String owner, String description, ProjectStatus status) {
        Project project = new Project();
        project.setId(idGenerator.getAndIncrement());
        project.setName(name);
        project.setOwner(owner);
        project.setDescription(description);
        project.setStatus(status);
        project.setCreatedAt(LocalDateTime.now().minusDays(new Random().nextInt(30)));
        projects.put(project.getId(), project);
    }
}
