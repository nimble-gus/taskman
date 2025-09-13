package com.taskman.controller;

import com.taskman.model.Project;
import com.taskman.model.ProjectStatus;
import com.taskman.service.ProjectService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Controller for Project management
 */
@Named
@ViewScoped
public class ProjectController implements Serializable {

    @Inject
    private ProjectService projectService;

    private List<Project> projects;
    private Project selectedProject;
    private Project newProject;
    private String searchTerm;
    private ProjectStatus statusFilter;
    private boolean showDialog;
    private boolean isEditMode;

    public void init() {
        loadProjects();
        initNewProject();
    }

    public void loadProjects() {
        projects = projectService.searchProjects(searchTerm, statusFilter);
    }

    public void search() {
        loadProjects();
    }

    public void clearSearch() {
        searchTerm = null;
        statusFilter = null;
        loadProjects();
    }

    public void initNewProject() {
        newProject = new Project();
        showDialog = true;
        isEditMode = false;
    }

    public void initEditProject(Project project) {
        newProject = new Project();
        newProject.setId(project.getId());
        newProject.setName(project.getName());
        newProject.setOwner(project.getOwner());
        newProject.setStatus(project.getStatus());
        newProject.setDescription(project.getDescription());
        newProject.setCreatedAt(project.getCreatedAt());
        showDialog = true;
        isEditMode = true;
    }

    public void saveProject() {
        try {
            if (isEditMode) {
                projectService.updateProject(newProject);
                addMessage("Proyecto actualizado exitosamente", FacesMessage.SEVERITY_INFO);
            } else {
                projectService.createProject(newProject);
                addMessage("Proyecto creado exitosamente", FacesMessage.SEVERITY_INFO);
            }
            loadProjects();
            closeDialog();
        } catch (Exception e) {
            addMessage("Error al guardar proyecto: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteProject(Project project) {
        try {
            projectService.deleteProject(project.getId());
            addMessage("Proyecto eliminado exitosamente", FacesMessage.SEVERITY_INFO);
            loadProjects();
        } catch (Exception e) {
            addMessage("Error al eliminar proyecto: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void closeDialog() {
        showDialog = false;
        newProject = null;
        isEditMode = false;
    }

    public void selectProject(Project project) {
        selectedProject = project;
    }

    public ProjectStatus[] getProjectStatuses() {
        return ProjectStatus.values();
    }

    public long getProjectCount() {
        return projectService.getProjectCount();
    }

    private void addMessage(String summary, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, null));
    }

    // Getters and Setters
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Project getNewProject() {
        return newProject;
    }

    public void setNewProject(Project newProject) {
        this.newProject = newProject;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public ProjectStatus getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(ProjectStatus statusFilter) {
        this.statusFilter = statusFilter;
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
