package ru.otus.services;

import java.util.List;

public interface GenreService {

    List<String> showAllGenres();

    void addGenre(String genreName);
}
