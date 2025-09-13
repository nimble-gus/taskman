package com.taskman.service;

import com.taskman.model.Project;
import com.taskman.model.ProjectStatus;
import com.taskman.repository.ProjectRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Project business logic
 */
@ApplicationScoped
public class ProjectService {

    @Inject
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> searchProjects(String searchTerm, ProjectStatus status) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return projectRepository.findByNameOrOwnerContainingAndStatus(searchTerm, status);
        } else if (status != null) {
            return projectRepository.findByStatus(status);
        } else {
            return projectRepository.findAll();
        }
    }

    public Project createProject(Project project) {
        validateProject(project);
        return projectRepository.save(project);
    }

    public Project updateProject(Project project) {
        validateProject(project);
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        projectRepository.deleteById(id);
    }

    public boolean isProjectNameUnique(String name, Long excludeId) {
        if (name == null || name.trim().isEmpty()) {
            return true;
        }
        
        if (excludeId == null) {
            return !projectRepository.existsByName(name);
        } else {
            return !projectRepository.existsByNameAndIdNot(name, excludeId);
        }
    }

    public long getProjectCount() {
        return projectRepository.count();
    }

    private void validateProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Project name is required");
        }
        
        if (project.getOwner() == null || project.getOwner().trim().isEmpty()) {
            throw new IllegalArgumentException("Project owner is required");
        }
        
        if (!isProjectNameUnique(project.getName(), project.getId())) {
            throw new IllegalArgumentException("Project name must be unique");
        }
    }
}
