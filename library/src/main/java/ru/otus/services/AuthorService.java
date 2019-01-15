package ru.otus.services;

public interface AuthorService {

    void showAuthorBooks(String authorName);

    void addAuthor(String authorName);

    void showAllAuthors();

    void addBookToAuthor(String authorName, String bookName);
}
