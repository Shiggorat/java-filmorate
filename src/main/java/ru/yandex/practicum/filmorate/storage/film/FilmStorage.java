package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    void addLike(Long id, Long userId);

    void removeLike(Long id, Long userId);

    List<Film> getPopularFilms(Long count);
}
