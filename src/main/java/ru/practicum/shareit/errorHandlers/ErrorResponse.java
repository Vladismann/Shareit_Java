package ru.practicum.shareit.errorHandlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private final String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public ErrorResponse(String error) {
        this.error = error;
    }
}