package ru.otus.dao;

import ru.otus.models.Author;

import java.util.List;

public interface AuthorDao {

    void save(Author author);

    Author getById(long id);

    List<Author> getAll();

    void update(Author author);

    void delete(long id);
    
    Author getByName(String lastName);
}
