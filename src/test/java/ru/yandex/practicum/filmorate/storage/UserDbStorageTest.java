package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {UserDbStorage.class})
@ComponentScan(basePackages = {"ru.yandex.practicum.filmorate.storage.user"})
class UserDbStorageTest {
    private final UserDbStorage storage;

    @Test
    void createUser() {
        storage.createUser(new User(
                1L,
                "new@test.ru",
                "login1",
                "name1",
                LocalDate.of(1991,01,12),
                null
        ));
        User user = storage.getUser(1L);
        assertThat(user).hasFieldOrPropertyWithValue("name", "new@test.ru");
        assertThat(user).hasFieldOrPropertyWithValue("email", "login1");
        assertThat(user).hasFieldOrPropertyWithValue("login", "name1");
        assertThat(user).hasFieldOrProperty("birthday");
    }

    @Test
    @Sql(scripts = {"/clear_all.sql","/test-get-users.sql"})
    void updateUser() {
        storage.updateUser(new User(
                1L,
                "updated@test.ru",
                "login1Updated",
                "name1Updated",
                LocalDate.of(1991,01,12),
                null
        ));
        User user = storage.getUser(1L);
        assertThat(user).hasFieldOrPropertyWithValue("name", "updated@test.ru");
        assertThat(user).hasFieldOrPropertyWithValue("email", "login1Updated");
        assertThat(user).hasFieldOrPropertyWithValue("login", "name1Updated");
        assertThat(user).hasFieldOrProperty("birthday");
    }

    @Test
    @Sql(scripts = {"/clear_all.sql","/test-get-users.sql"})
    void getUser() {
        List<User> users = storage.getAllUsers();
        assertThat(users.getFirst()).hasFieldOrPropertyWithValue("email", "email1@email.ru");
        assertThat(users.getFirst()).hasFieldOrPropertyWithValue("login", "login1");
        assertThat(users.getFirst()).hasFieldOrPropertyWithValue("name", "name1");
        assertThat(users.getFirst()).hasFieldOrProperty("birthday");
    }

    @Test
    @Sql(scripts = {"/clear_all.sql","/test-get-users.sql"})
    void getAllUsers() {
        List<User> users = storage.getAllUsers();
        assertThat(users.getFirst()).hasFieldOrPropertyWithValue("email", "email1@email.ru");
        assertThat(users.getFirst()).hasFieldOrPropertyWithValue("login", "login1");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("name", "name1");
        assertThat(users.get(0)).hasFieldOrProperty("birthday");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("email", "email2@email.ru");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("login", "login2");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("name", "name2");
        assertThat(users.get(1)).hasFieldOrProperty("birthday");
        assertThat(users.get(2)).hasFieldOrPropertyWithValue("email", "email3@email.ru");
        assertThat(users.get(2)).hasFieldOrPropertyWithValue("login", "login3");
        assertThat(users.get(2)).hasFieldOrPropertyWithValue("name", "name3");
        assertThat(users.get(2)).hasFieldOrProperty("birthday");
    }
}
