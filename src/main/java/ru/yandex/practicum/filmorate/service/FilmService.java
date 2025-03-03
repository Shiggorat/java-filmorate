package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    public Collection<Film> findAll() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film create(Film film) {
        inMemoryFilmStorage.createFilm(film);
        return film;
    }

    public Film update(Film filmUpdate) {
        return inMemoryFilmStorage.updateFilm(filmUpdate);
    }

    public void addLikeToFilm(Long filmId, Long userId) {
        inMemoryFilmStorage.addLike(filmId, userId);
    }

    public void removeLikeFromFilm(Long filmId, Long userId) {
        inMemoryFilmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Long count) {
        return inMemoryFilmStorage.getPopularFilms(count);
    }
}
