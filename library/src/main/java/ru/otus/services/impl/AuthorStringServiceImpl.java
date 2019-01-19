package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.services.AuthorService;
import ru.otus.services.AuthorStringService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorStringServiceImpl implements AuthorStringService {

    private AuthorService authorService;

    public AuthorStringServiceImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public List<String> showAllAuthors() {
        List<Author> authors = authorService.getAll();
        List<String> authorNameList = new ArrayList<>();
        for (Author author : authors) {
            authorNameList.add(author.getName());
        }
        return authorNameList;
    }
}