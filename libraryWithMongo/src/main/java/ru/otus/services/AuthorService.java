package ru.otus.services;

import ru.otus.models.Author;

import java.util.List;

public interface AuthorService {

    void addAuthor(String authorName);

    List<Author> getAll();

    Author getByName(String name);

    void deleteAuthor(String authorName);
}
