package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.annotation.AfterDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Validated
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Имейл не указан или указан без @")
    private String email;
    @NotBlank(message = "Логин не указан или содержит пробел")
    private String login;
    @AfterDate(message = "Дата рождения указана неверно")
    private LocalDate birthday;
    private Set<Long> friends;

    public User() {
        this.friends = new HashSet<>();
    }

    public void userAddFriend(Long userId) {
        this.friends.add(userId);
    }

    public void userRemoveFriend(Long userId) {
        this.friends.remove(userId);
    }
}
