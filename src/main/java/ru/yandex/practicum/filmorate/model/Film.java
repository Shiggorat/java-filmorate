package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.BeforeDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длинна описания - 200 символов")
    private String description;
    @BeforeDate(message = "Дата выпуска фильма не может быть раньше 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть меньше или равна 0")
    private Integer duration;
    private Set<Long> likes;

    public Film() {
        this.likes = new HashSet<>();
    }

    public void filmAddLike(Long userId) {
        this.likes.add(userId);
    }

    public void filmRemoveLike(Long userId) {
        this.likes.remove(userId);
    }

    public int getAllLikes() {
        return this.likes.size();
    }
 }
