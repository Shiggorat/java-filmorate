package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.BeforeDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Validated
@EqualsAndHashCode
@Builder(toBuilder = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длинна описания - 200 символов")
    private String description;
    @BeforeDate(message = "Дата выпуска фильма не может быть раньше 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть меньше 0")
    private Integer duration;
    private List<Long> likes;
    private List<Genre> genres;
    @NonNull
    private Mpa mpa;
 }
