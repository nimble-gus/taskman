package com.taskman.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 * Controller for navigation menu
 */
@Named
@ApplicationScoped
public class NavigationController {

    private MenuModel model;

    public NavigationController() {
        model = new DefaultMenuModel();
        
        // Dashboard item
        DefaultMenuItem dashboardItem = new DefaultMenuItem();
        dashboardItem.setValue("Dashboard");
        dashboardItem.setIcon("fa fa-dashboard");
        dashboardItem.setUrl("/pages/dashboard.xhtml");
        model.getElements().add(dashboardItem);
        
        // Projects item
        DefaultMenuItem projectsItem = new DefaultMenuItem();
        projectsItem.setValue("Proyectos");
        projectsItem.setIcon("fa fa-folder");
        projectsItem.setUrl("/pages/projects.xhtml");
        model.getElements().add(projectsItem);
    }

    public MenuModel getModel() {
        return model;
    }
}
