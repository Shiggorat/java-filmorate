package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    @Autowired
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film filmUpdate) {
        return filmService.update(filmUpdate);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLikeToFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLikeToFilm(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLikeFromFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.removeLikeFromFilm(filmId, userId);
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable Long filmId) {
        return filmService.getFilm(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Long count) {
        return filmService.getPopularFilms(count);
    }
}
