package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.models.Author;
import ru.otus.services.AuthorService;
import ru.otus.services.InputOutputService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private InputOutputService inputOutputService;

    public AuthorServiceImpl(AuthorDao authorDao, InputOutputService inputOutputService) {
        this.authorDao = authorDao;
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void showAuthorBooks(String authorName) {
        authorDao.save(new Author(authorName));
    }

    @Override
    public void addAuthor(String authorName) {
        List<Author> authors = authorDao.getAll();
        for (Author author : authors) {
            inputOutputService.out(author.toString());
        }
    }
}