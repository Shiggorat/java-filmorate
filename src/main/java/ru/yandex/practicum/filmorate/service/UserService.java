package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        return inMemoryUserStorage.createUser(user);
    }

    public User update(User userUpdate) {
        return inMemoryUserStorage.updateUser(userUpdate);
    }

    public void addFriend(Long id, Long friendId) {
        inMemoryUserStorage.addFriends(id, friendId);
    }

    public void removeFriend(Long id, Long friendId) {
        inMemoryUserStorage.removeFriends(id, friendId);
    }

    public List<User> getUserFriendsList(Long id) {
        return inMemoryUserStorage.getFriends(id);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        return inMemoryUserStorage.getCommonFriends(id, otherId);
    }
}
