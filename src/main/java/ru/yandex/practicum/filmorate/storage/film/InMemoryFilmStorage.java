package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();


    @Override
    public Film createFilm(Film film) {
        log.debug("Starting post {}", film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.debug("Posted {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.debug("Starting update {}", film);
        Film oldFilm = films.get(film.getId());
        oldFilm.setName(film.getName());
        oldFilm.setDescription(film.getDescription());
        oldFilm.setDuration(film.getDuration());
        oldFilm.setReleaseDate(film.getReleaseDate());
        log.debug("Updated {}", film);
        return oldFilm;
    }

    @Override
    public List<Film> getAllFilms() {
        return films.values().stream().toList();
    }

    public List<Long> getAllFilmsID() {
        return films.keySet().stream().toList();
    }

    @Override
    public void addLike(Long id, Long userId) {
        films.get(id).filmAddLike(userId);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        films.get(id).filmRemoveLike(userId);
    }

    @Override
    public List<Film> getPopularFilms(Long count) {
        return films.values().stream()
                .sorted(Comparator.comparing(Film::getAllLikes).reversed())
                .limit(count)
                .toList();
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
