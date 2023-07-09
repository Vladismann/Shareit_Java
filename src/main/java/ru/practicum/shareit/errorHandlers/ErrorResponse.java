package ru.practicum.shareit.errorHandlers;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorResponse {
    private final String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String error) {
        this.error = error;
        message = null;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}