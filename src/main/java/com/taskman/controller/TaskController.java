package com.taskman.controller;

import com.taskman.model.Project;
import com.taskman.model.Task;
import com.taskman.model.TaskPriority;
import com.taskman.service.ProjectService;
import com.taskman.service.TaskService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Task management
 */
@Named
@ViewScoped
public class TaskController implements Serializable {

    @Inject
    private TaskService taskService;

    @Inject
    private ProjectService projectService;

    private List<Task> tasks;
    private Task selectedTask;
    private Task newTask;
    private Project currentProject;
    private TaskPriority priorityFilter;
    private Boolean doneFilter;
    private boolean showDialog;
    private boolean isEditMode;

    public void init() {
        if (currentProject != null) {
            loadTasks();
            initNewTask();
        }
    }

    public void loadTasks() {
        if (currentProject == null) {
            return;
        }

        if (priorityFilter != null && doneFilter != null) {
            // Filter by both priority and done status
            tasks = taskService.getTasksByProjectIdAndPriority(currentProject.getId(), priorityFilter);
            tasks = tasks.stream()
                    .filter(task -> task.isDone() == doneFilter)
                    .collect(java.util.stream.Collectors.toList());
        } else if (priorityFilter != null) {
            tasks = taskService.getTasksByProjectIdAndPriority(currentProject.getId(), priorityFilter);
        } else if (doneFilter != null) {
            tasks = taskService.getTasksByProjectIdAndDone(currentProject.getId(), doneFilter);
        } else {
            tasks = taskService.getTasksByProjectId(currentProject.getId());
        }
    }

    public void filterTasks() {
        loadTasks();
    }

    public void clearFilters() {
        priorityFilter = null;
        doneFilter = null;
        loadTasks();
    }

    public void initNewTask() {
        newTask = new Task();
        newTask.setProjectId(currentProject.getId());
        newTask.setDueDate(LocalDate.now().plusDays(7)); // Default to 7 days from now
        showDialog = true;
        isEditMode = false;
    }

    public void initEditTask(Task task) {
        newTask = new Task();
        newTask.setId(task.getId());
        newTask.setProjectId(task.getProjectId());
        newTask.setTitle(task.getTitle());
        newTask.setPriority(task.getPriority());
        newTask.setDueDate(task.getDueDate());
        newTask.setDone(task.isDone());
        newTask.setNotes(task.getNotes());
        showDialog = true;
        isEditMode = true;
    }

    public void saveTask() {
        try {
            if (isEditMode) {
                taskService.updateTask(newTask);
                addMessage("Tarea actualizada exitosamente", FacesMessage.SEVERITY_INFO);
            } else {
                taskService.createTask(newTask);
                addMessage("Tarea creada exitosamente", FacesMessage.SEVERITY_INFO);
            }
            loadTasks();
            closeDialog();
        } catch (Exception e) {
            addMessage("Error al guardar tarea: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteTask(Task task) {
        try {
            taskService.deleteTask(task.getId());
            addMessage("Tarea eliminada exitosamente", FacesMessage.SEVERITY_INFO);
            loadTasks();
        } catch (Exception e) {
            addMessage("Error al eliminar tarea: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void toggleTaskCompletion(Task task) {
        try {
            taskService.toggleTaskCompletion(task.getId());
            addMessage("Estado de tarea actualizado", FacesMessage.SEVERITY_INFO);
            loadTasks();
        } catch (Exception e) {
            addMessage("Error al actualizar tarea: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void closeDialog() {
        showDialog = false;
        newTask = null;
        isEditMode = false;
    }

    public boolean isTaskOverdue(Task task) {
        return taskService.isTaskOverdue(task);
    }

    public String getRowStyleClass(Task task) {
        return isTaskOverdue(task) ? "row-danger" : "";
    }

    public TaskPriority[] getTaskPriorities() {
        return TaskPriority.values();
    }

    public long getOpenTaskCount() {
        return taskService.getTaskCountByProjectIdAndDone(currentProject.getId(), false);
    }

    public long getCompletedTaskCount() {
        return taskService.getTaskCountByProjectIdAndDone(currentProject.getId(), true);
    }

    public long getTotalTaskCount() {
        return taskService.getTaskCountByProjectId(currentProject.getId());
    }

    private void addMessage(String summary, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, null));
    }

    // Getters and Setters
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Task getNewTask() {
        return newTask;
    }

    public void setNewTask(Task newTask) {
        this.newTask = newTask;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
        if (currentProject != null) {
            init();
        }
    }

    public TaskPriority getPriorityFilter() {
        return priorityFilter;
    }

    public void setPriorityFilter(TaskPriority priorityFilter) {
        this.priorityFilter = priorityFilter;
    }

    public Boolean getDoneFilter() {
        return doneFilter;
    }

    public void setDoneFilter(Boolean doneFilter) {
        this.doneFilter = doneFilter;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }
}
