package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    public Collection<Film> findAll() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film create(Film film) {
        inMemoryFilmStorage.createFilm(film);
        return film;
    }

    public Film update(Film filmUpdate) {
        if (filmUpdate.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (!inMemoryFilmStorage.getAllFilmsID().contains(filmUpdate.getId())) {
            log.error("такого айди нет {}", filmUpdate.getId());
            throw new NotFoundException("Фильм с id = " + filmUpdate.getId() + " не найден");
        }
        return inMemoryFilmStorage.updateFilm(filmUpdate);
    }

    public void addLikeToFilm(Long filmId, Long userId) {
        if (!inMemoryFilmStorage.getAllFilmsID().contains(filmId)) {
            log.error("ошибка с id  {}", filmId);
            throw new NotFoundException("Фильма с таким id найдено");
        }
        if (!inMemoryUserStorage.getUsersId().contains(userId)) {
            log.error("ошибка с id  {}", userId);
            throw new NotFoundException("Юзера с таким id найдено");
        }
        inMemoryFilmStorage.addLike(filmId, userId);
    }

    public void removeLikeFromFilm(Long filmId, Long userId) {
        if (!inMemoryFilmStorage.getAllFilmsID().contains(filmId)) {
            log.error("ошибка с id  {}", filmId);
            throw new NotFoundException("Фильма с таким id найдено");
        }
        if (!inMemoryUserStorage.getUsersId().contains(userId)) {
            log.error("ошибка с id  {}", userId);
            throw new NotFoundException("Юзера с таким id найдено");
        }
        inMemoryFilmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Long count) {
        return inMemoryFilmStorage.getPopularFilms(count);
    }
}
