package ru.otus.dao;

import ru.otus.models.Genre;

import java.util.List;

public interface GenreDao {

    Genre getGenreById(long id);

    Genre getByName(String name);

    void addGenre(Genre genre);

    void deleteGenre(long id);

    List getAll();
}
