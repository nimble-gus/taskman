package com.taskman.validation;

import com.taskman.service.ProjectService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;

/**
 * Custom validator to ensure project names are unique
 */
@FacesValidator("uniqueProjectNameValidator")
public class UniqueProjectNameValidator implements Validator {

    @Inject
    private ProjectService projectService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().trim().isEmpty()) {
            return; // Let required validation handle empty values
        }

        String projectName = value.toString().trim();
        
        // Get the current project ID from the component attributes (for edit scenarios)
        Object currentProjectId = component.getAttributes().get("currentProjectId");
        Long projectId = null;
        if (currentProjectId != null) {
            try {
                projectId = Long.valueOf(currentProjectId.toString());
            } catch (NumberFormatException e) {
                // Ignore invalid project ID
            }
        }

        // Check if the project name is unique
        if (!projectService.isProjectNameUnique(projectName, projectId)) {
            String message = "El nombre del proyecto '" + projectName + "' ya existe. Por favor, elija otro nombre.";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            throw new ValidatorException(facesMessage);
        }
    }
}
