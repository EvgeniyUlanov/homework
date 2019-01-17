package ru.otus.services;

import java.util.List;

public interface AuthorStringService {

    List<String> showAuthorBooks(String authorName);

    void addAuthor(String authorName);

    List<String> showAllAuthors();

    void addBookToAuthor(String authorName, String bookName);
}
