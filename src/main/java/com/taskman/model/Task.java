package com.taskman.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Model class representing a Task
 */
public class Task {
    private Long id;
    private Long projectId;
    private String title;
    private TaskPriority priority;
    private LocalDate dueDate;
    private boolean done;
    private String notes;

    public Task() {
        this.priority = TaskPriority.MEDIUM;
        this.done = false;
    }

    public Task(Long projectId, String title, TaskPriority priority, LocalDate dueDate, String notes) {
        this();
        this.projectId = projectId;
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", done=" + done +
                ", notes='" + notes + '\'' +
                '}';
    }
}
