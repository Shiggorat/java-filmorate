package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    void addFriends(Long userId, Long friendId);

    void removeFriends(Long userId, Long friendId);

    List<User> getFriends(Long id);

    List<User> getCommonFriends(Long id, Long friendId);
}
