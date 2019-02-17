package ru.otus.services;

import ru.otus.models.Book;

import java.util.List;

public interface BookService {

    Book getBookByName(String bookName);

    Book getBookById(String id);

    List<Book> getAllBooks();

    List<Book> getBookByGenre(String genre);

    void addBook(String bookName, String genre, String authorName);

    List<Book> getBookByAuthor(String authorName);

    void addCommentToBook(String bookName, String comment);

    List<String> getCommentsByBook(String bookName);

    void addAuthorToBook(String authorName, String bookName);

    void deleteBook(String bookName);
}
