package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Starting post {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Posted {}", user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    public User update(@Valid @RequestBody User userUpdate) {
        log.debug("Starting update {}", userUpdate);
        if (userUpdate.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (users.containsKey(userUpdate.getId())) {
            User oldUser = users.get(userUpdate.getId());
            oldUser.setLogin(userUpdate.getLogin());
            oldUser.setEmail(userUpdate.getEmail());
            oldUser.setBirthday(userUpdate.getBirthday());
            if (userUpdate.getName() != null) {
                oldUser.setName(userUpdate.getName());
            }
            log.debug("Updated {}", userUpdate);
            return oldUser;
        }
        log.error("ошибка с id {}", userUpdate.getId());
        throw new ValidationException("Пользователь с id = " + userUpdate.getId() + " не найден");
    }
}
