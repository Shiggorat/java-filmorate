package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        log.debug("Starting post {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Posted {}", user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        log.debug("Starting update {}", user);
        if (user.getId() == null) {
            log.error("нет айди");
            throw new ValidationException("Id должен быть указан");
        }
        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            oldUser.setLogin(user.getLogin());
            oldUser.setEmail(user.getEmail());
            oldUser.setBirthday(user.getBirthday());
            if (user.getName() != null) {
                oldUser.setName(user.getName());
            }
            log.debug("Updated {}", user);
            return oldUser;
        }
        log.error("ошибка с id {}", user.getId());
        throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
    }

    @Override
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }

    public List<Long> getUsersId() {
        return users.keySet().stream().toList();
    }

    @Override
    public void addFriends(Long userId, Long friendId) {
        if (!users.containsKey(userId) || !users.containsKey(friendId)) {
            log.error("ошибка с id  {}", userId);
            throw new NotFoundException("Таких id найдено");
        }
        users.get(userId).userAddFriend(friendId);
        users.get(friendId).userAddFriend(userId);
    }

    @Override
    public void removeFriends(Long userId, Long friendId) {
        if (!users.containsKey(userId) || !users.containsKey(friendId)) {
            log.error("ошибка с id  {}", userId);
            throw new NotFoundException("Таких id найдено");
        }
        users.get(userId).userRemoveFriend(friendId);
        users.get(friendId).userRemoveFriend(userId);
    }

    @Override
    public List<User> getFriends(Long id) {
        if (!users.containsKey(id)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Такого id найдено");
        }
        Set<Long> userFriendsId = users.get(id).getFriends();
        List<User> userFriends = new ArrayList<>();
        for (Long friend : userFriendsId) {
            userFriends.add(users.get(friend));
        }
        return userFriends;
    }

    @Override
    public List<User> getCommonFriends(Long id, Long friendId) {
        if (!users.containsKey(id) || !users.containsKey(friendId)) {
            log.error("ошибка с id  {}", id);
            throw new NotFoundException("Таких id найдено");
        }
        List<User> user1 = getFriends(id);
        List<User> user2 = getFriends(friendId);
        return user1.stream().filter(user2::contains).toList();
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
