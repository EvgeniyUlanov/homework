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
        Author author = getByName(authorName);
        if (author == null) {
            authorRepository.save(new Author(authorName));
        }
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.findByName(name).orElse(null);
    }

    @Override
    public void deleteAuthor(String authorName) {
        authorRepository.findByName(authorName).ifPresent(author -> authorRepository.delete(author));
    }
}
