package com.taskman.model;

/**
 * Enum representing the priority of a task
 */
public enum TaskPriority {
    LOW("Baja"),
    MEDIUM("Media"),
    HIGH("Alta");

    private final String displayName;

    TaskPriority(String displayName) {
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
