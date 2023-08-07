package ru.practicum.shareit.errorHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ru.practicum.shareit.common.Messages.INCORRECT_DATA;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        return new ErrorResponse("Некорректные данные", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNotFound(final Throwable e) {
        log.info(e.getMessage());
        return new ErrorResponse("Произошла ошибка", INCORRECT_DATA);
    }
}