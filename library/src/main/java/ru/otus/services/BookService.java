package ru.otus.services;

public interface BookService {

    void showBookByName(String bookName);

    void showBookById(Long id);

    void showAllBooks();

    void showBookByGenre(String genre);

    void showBookByAuthor(String author);

    void addBook(String bookName, String genre, String authorName);

    void addAuthorToBook(String authorName, String bookName);
}
