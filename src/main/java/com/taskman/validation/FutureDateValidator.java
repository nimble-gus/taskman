package com.taskman.validation;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import java.time.LocalDate;

/**
 * Custom validator to ensure dates are not in the past
 */
@FacesValidator("futureDateValidator")
public class FutureDateValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return; // Let required validation handle null values
        }

        LocalDate date;
        if (value instanceof LocalDate) {
            date = (LocalDate) value;
        } else if (value instanceof java.util.Date) {
            date = ((java.util.Date) value).toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            // Try to parse as string
            try {
                date = LocalDate.parse(value.toString());
            } catch (Exception e) {
                String message = "Formato de fecha inválido";
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
                throw new ValidatorException(facesMessage);
            }
        }

        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            String message = "La fecha no puede ser anterior a hoy (" + today + ")";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            throw new ValidatorException(facesMessage);
        }
    }
}
