package ru.otus.services;

import java.util.List;

public interface BookService {

    String showBookByName(String bookName);

    String showBookById(Long id);

    List<String> showAllBooks();

    List<String> showBookByGenre(String genre);

    String addBook(String bookName, String genre, String authorName);
}
