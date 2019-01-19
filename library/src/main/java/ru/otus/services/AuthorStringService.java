package ru.otus.services;

import java.util.List;

public interface AuthorStringService {

    void addAuthor(String authorName);

    List<String> showAllAuthors();

    void addBookToAuthor(String authorName, String bookName);
}
