package com.klausteles.todosimple.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    // Construtor para os campos finais (status e message)
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    // Setters
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    // Método para adicionar um erro de validação à lista de erros
    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ValidationError(field, message));
    }

    // Método para converter a resposta de erro em JSON (simplificado)
    public String toJson() {
        return "{\"status\": " + getStatus() + ", " +
                "\"message\": \"" + getMessage() + "\"}";
    }

    // Classe ValidationError
    public static class ValidationError {
        private final String field;
        private final String message;

        // Construtor para os campos finais (field e message)
        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        // Getters
        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}

