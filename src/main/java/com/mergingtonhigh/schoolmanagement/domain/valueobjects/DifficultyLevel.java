package com.mergingtonhigh.schoolmanagement.domain.valueobjects;

/**
 * Enum representing the difficulty levels of activities.
 */
public enum DifficultyLevel {
    INICIANTE("Iniciante"),
    INTERMEDIARIO("Intermediário"),
    AVANCADO("Avançado");

    private final String label;

    DifficultyLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}