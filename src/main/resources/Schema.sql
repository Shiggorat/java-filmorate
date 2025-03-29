DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films_genre;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS rating_mpa;

CREATE TABLE IF NOT EXISTS users (
              id BIGINT GENERATED BY DEFAULT AS IDENTITY,
              email VARCHAR(100) NOT NULL,
              login VARCHAR(100) NOT NULL,
              name VARCHAR(100),
              birthday DATE NOT NULL,
              PRIMARY KEY (id)
              );

CREATE TABLE IF NOT EXISTS films (
              id BIGINT GENERATED BY DEFAULT AS IDENTITY,
              name VARCHAR(250) NOT NULL,
              description VARCHAR(256) NOT NULL,
              release_date DATE,
              duration INT NOT NULL,
              rating_mpa_id BIGINT,
              PRIMARY KEY (id)
              );

CREATE TABLE IF NOT EXISTS rating_mpa (
              id BIGINT GENERATED BY DEFAULT AS IDENTITY,
              name VARCHAR(20),
              PRIMARY KEY (id)
              );

CREATE TABLE IF NOT EXISTS genres (
              id BIGINT GENERATED BY DEFAULT AS IDENTITY,
              name VARCHAR(50),
              PRIMARY KEY (id)
              );

CREATE TABLE IF NOT EXISTS films_genre (
              film_id BIGINT,
              genre_id BIGINT,
              PRIMARY KEY (film_id, genre_id)
              );

CREATE TABLE IF NOT EXISTS likes (
              film_id BIGINT,
              user_id BIGINT,
              PRIMARY KEY (film_id,user_id)
              );

CREATE TABLE IF NOT EXISTS friends (
              user1_id BIGINT NOT NULL,
              user2_id BIGINT NOT NULL,
              status BOOL,
              PRIMARY KEY (user1_id, user2_id)
              );

ALTER TABLE friends ADD FOREIGN KEY (user1_id) REFERENCES users (id);
ALTER TABLE friends ADD FOREIGN KEY (user2_id) REFERENCES users (id);
ALTER TABLE films ADD FOREIGN KEY (rating_mpa_id) REFERENCES rating_mpa (id);
ALTER TABLE likes ADD FOREIGN KEY (film_id) REFERENCES films (id);
ALTER TABLE likes ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE films_genre ADD FOREIGN KEY (film_id) REFERENCES films (id);
ALTER TABLE films_genre ADD FOREIGN KEY (genre_id) REFERENCES genres (id);