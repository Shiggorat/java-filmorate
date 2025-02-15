package ru.yandex.practicum.filmorate.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.controller.FilmController;


@ControllerAdvice
public class ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationException handleValidationException(MethodArgumentNotValidException e) {
        log.error("Ошибка валидации аргумента");
        return new ValidationException(e.getMessage());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationException handleValidationException(ConstraintViolationException e) {
        log.error("Ошибка валидации");
        return new ValidationException(e.getMessage());

    }
}