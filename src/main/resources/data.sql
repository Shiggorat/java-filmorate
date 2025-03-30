INSERT INTO genres (name)
SELECT name FROM (VALUES
    ('Комедия'),
    ('Драма'),
    ('Мультфильм'),
    ('Триллер'),
    ('Документальный'),
    ('Боевик')
) AS source (name)
WHERE NOT EXISTS (SELECT 1 FROM genres WHERE genres.name = source.name);

INSERT INTO rating_mpa (name)
SELECT name FROM (VALUES
    ('G'),
    ('PG'),
    ('PG-13'),
    ('R'),
    ('NC-17')
) AS source (name)
WHERE NOT EXISTS (SELECT 1 FROM rating_mpa WHERE rating_mpa.name = source.name);