package com.taskman.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model class representing a Project
 */
public class Project {
    private Long id;
    private String name;
    private String owner;
    private ProjectStatus status;
    private LocalDateTime createdAt;
    private String description;

    public Project() {
        this.createdAt = LocalDateTime.now();
        this.status = ProjectStatus.ACTIVE;
    }

    public Project(String name, String owner, String description) {
        this();
        this.name = name;
        this.owner = owner;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                '}';
    }
}
