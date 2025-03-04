package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    public Collection<User> findAll() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return inMemoryUserStorage.createUser(user);
    }

    public User update(User userUpdate) {
        if (userUpdate.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (!inMemoryUserStorage.getUsersId().contains(userUpdate.getId())) {
            log.error("ошибка с id {}", userUpdate.getId());
            throw new NotFoundException("Пользователь с id = " + userUpdate.getId() + " не найден");
        }
        return inMemoryUserStorage.updateUser(userUpdate);
    }

    public void addFriend(Long id, Long friendId) {
        if (!inMemoryUserStorage.getUsersId().contains(id) || !inMemoryUserStorage.getUsersId().contains(friendId)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        inMemoryUserStorage.addFriends(id, friendId);
    }

    public void removeFriend(Long id, Long friendId) {
        if (!inMemoryUserStorage.getUsersId().contains(id) || !inMemoryUserStorage.getUsersId().contains(friendId)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        inMemoryUserStorage.removeFriends(id, friendId);
    }

    public List<User> getUserFriendsList(Long id) {
        if (!inMemoryUserStorage.getUsersId().contains(id)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Такого id найдено");
        }
        return inMemoryUserStorage.getFriends(id);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        if (!inMemoryUserStorage.getUsersId().contains(id) || !inMemoryUserStorage.getUsersId().contains(otherId)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        return inMemoryUserStorage.getCommonFriends(id, otherId);
    }
}
