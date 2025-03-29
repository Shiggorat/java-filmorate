package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.getAllUsers();
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.createUser(user);
    }

    public User update(User userUpdate) {
        if (userUpdate.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (userStorage.getUser(userUpdate.getId())  == null) {
            log.error("ошибка с id {}", userUpdate.getId());
            throw new NotFoundException("Пользователь с id = " + userUpdate.getId() + " не найден");
        }
        return userStorage.updateUser(userUpdate);
    }

    public User getUser(Long userId) {
        if (userStorage.getUser(userId) != null) {
            return userStorage.getUser(userId);
        }
        throw new NotFoundException("Пользователь не найден с id = " + userId);
    }

    public void addFriend(Long id, Long friendId) {
        if ((userStorage.getUser(id) == null) || (userStorage.getUser(friendId) == null)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        userStorage.addFriends(id, friendId);
    }

    public void removeFriend(Long id, Long friendId) {
        if ((userStorage.getUser(id) == null) || (userStorage.getUser(friendId) == null)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        userStorage.removeFriends(id, friendId);
    }

    public List<User> getUserFriendsList(Long id) {
        if (userStorage.getUser(id) == null) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Такого id найдено");
        }
        return userStorage.getFriends(id);
    }

    public List<User> getCommonFriends(Long id, Long friendId) {
        if ((userStorage.getUser(id) == null) || (userStorage.getUser(friendId) == null)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        return userStorage.getCommonFriends(id, friendId);
    }
}
