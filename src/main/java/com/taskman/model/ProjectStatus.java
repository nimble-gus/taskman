package com.taskman.model;

/**
 * Enum representing the status of a project
 */
public enum ProjectStatus {
    ACTIVE("Activo"),
    ON_HOLD("En Espera"),
    DONE("Completado");

    private final String displayName;

    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
