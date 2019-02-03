package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.models.Author;
import ru.otus.repositories.AuthorMongoRepository;
import ru.otus.services.AuthorService;

import java.util.List;

@Service
public class AuthorServiceWithMongo implements AuthorService {

    private AuthorMongoRepository authorRepository;

    public AuthorServiceWithMongo(AuthorMongoRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void addAuthor(String authorName) {
        Author author = new Author(authorName);
        author.setId(1L);
        authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getByName(String name) {
        return null;
    }

    @Override
    public void addBookToAuthor(String authorName, String bookName) {

    }
}
