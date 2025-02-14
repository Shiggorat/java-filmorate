package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Data
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();
    final private LocalDate FIRST_MOVIE_DATE = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Starting post {}", film);
//        if (film.getReleaseDate().isBefore(FIRST_MOVIE_DATE)) {
//            log.error("дата выпуска раньше 28.12.1895");
//            throw new ValidationException("Дата выпуска фильма не может быть раньше 28.12.1895");
//        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.debug("Posted {}", film);
        return film;
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film filmUpdate) {
        log.debug("Starting update {}", filmUpdate);
        if (filmUpdate.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (films.containsKey(filmUpdate.getId())) {
            Film oldFilm = films.get(filmUpdate.getId());
            oldFilm.setName(filmUpdate.getName());
            oldFilm.setDescription(filmUpdate.getDescription());
            oldFilm.setDuration(filmUpdate.getDuration());
            oldFilm.setReleaseDate(filmUpdate.getReleaseDate());
            log.debug("Updated {}", filmUpdate);
            return oldFilm;
        }
        log.error("такого айди нет {}", filmUpdate.getId());
        throw new ValidationException("Фильм с id = " + filmUpdate.getId() + " не найден");
    }
}
