package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class FilmControllerTest {
    Film film;
    private Validator validator;

    @BeforeEach
    void start() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        film = Film.builder()
                .name("film1")
                .description("descriptionFilm1")
                .releaseDate(LocalDate.of(2000,12,12))
                .duration(100)
                .mpa(new Mpa(1L,"G"))
                .build();
    }

    @Test
    public void shouldNotCreateWithNegativeDuration() {
        film.setDuration(-1);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldNotCreateWithInvalidReleaseDate() {
        film.setReleaseDate(LocalDate.of(1800,1,1));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNoCreateWithEmptyName() {
        film.setName("");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());

    }

    @Test
    void shouldNotCreateWithDescriptionOver200Symbols() {
        film.setDescription("Ф".repeat(201));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
