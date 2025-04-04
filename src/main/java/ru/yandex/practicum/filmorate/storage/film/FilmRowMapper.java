package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    Map<Long, Film> filmMap = new HashMap<>();


    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long filmId = resultSet.getLong("id");
        Film film = filmMap.get(filmId);
        if (film == null) {
            film = Film.builder()
                    .id(filmId)
                    .name(resultSet.getString("name"))
                    .description(resultSet.getString("description"))
                    .releaseDate(resultSet.getDate("release_date").toLocalDate())
                    .duration(resultSet.getInt("duration"))
                    .genres(new ArrayList<Genre>())
                    .likes(new ArrayList<Long>())
                    .mpa(Mpa.builder()
                            .id(resultSet.getLong("mpa_id"))
                            .name(resultSet.getString("mpa_name"))
                            .build())
                    .build();
            filmMap.put(filmId, film);
        }
        if (resultSet.getLong("like_id") != 0) {
            if (!film.getLikes().contains(resultSet.getLong("like_id"))) {
                film.getLikes().add(resultSet.getLong("like_id"));
            }
        }
        if (resultSet.getLong("genre_id") != 0) {
            if (!film.getGenres().contains(Genre.builder()
                    .id(resultSet.getLong("genre_id"))
                    .name(resultSet.getString("genre_name"))
                    .build())) {
                film.getGenres().add(Genre.builder()
                        .id(resultSet.getLong("genre_id"))
                        .name(resultSet.getString("genre_name"))
                        .build());
            }
        }
        if (resultSet.isLast()) {
            filmMap.clear();
        }
        return film;
    }
}
