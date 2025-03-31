package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.getAllFilms();
    }

    public Film create(Film film) {
        if (mpaStorage.getById(film.getMpa().getId()) == null) {
            throw new NotFoundException("Рейтинг + " + film.getMpa() + " не найден");
        }
        if (film.getGenres() != null) {
            genreStorage.checkGenresExists(film.getGenres());
        }
        return filmStorage.createFilm(film);
    }

    public Film update(Film filmUpdate) {
        if (filmUpdate.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (filmStorage.getFilm(filmUpdate.getId()) == null) {
            log.error("такого фильма нет {}", filmUpdate.getId());
            throw new NotFoundException("Фильм с id = " + filmUpdate.getId() + " не найден");
        }
        return filmStorage.updateFilm(filmUpdate);
    }

    public Film getFilm(Long filmId) {
        if (filmStorage.getFilm(filmId) != null) {
            return filmStorage.getFilm(filmId);
        }
        throw new NotFoundException("Фильм с id = " + filmId + " не найден");
    }

    public void addLikeToFilm(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId) == null) {
            log.error("ошибка с id фильма  {}", filmId);
            throw new NotFoundException("Фильма с таким id найдено");
        }
        if (userStorage.getUser(userId) == null) {
            log.error("ошибка с id юзера  {}", userId);
            throw new NotFoundException("Юзера с таким id найдено");
        }
        filmStorage.checkLikeOnFilm(filmId,userId);
        filmStorage.addLike(filmId, userId);
    }

    public void removeLikeFromFilm(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId) == null) {
            log.error("ошибка с id фильма  {}", filmId);
            throw new NotFoundException("Фильма с таким id найдено");
        }
        if (userStorage.getUser(userId) == null) {
            log.error("ошибка с id юзера  {}", userId);
            throw new NotFoundException("Юзера с таким id найдено");
        }
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Long count) {
        return filmStorage.getPopularFilms(count);
    }
}
