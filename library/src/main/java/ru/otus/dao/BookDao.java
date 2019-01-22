package ru.otus.dao;

import ru.otus.models.Author;
import ru.otus.models.Book;

import java.util.List;

public interface BookDao {

    void save(Book book);

    Book getById(long id);

    List<Book> getAll();

    Book getByName(String name);

    List<Book> getByGenre(String genre);

    List<Book> getByAuthor(Author author);

    void update(Book book);

    void delete(long id);
}
