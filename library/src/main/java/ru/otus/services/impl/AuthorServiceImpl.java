package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.services.AuthorService;
import ru.otus.services.InputOutputService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private InputOutputService inputOutputService;
    private BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao, InputOutputService inputOutputService, BookDao bookDao) {
        this.authorDao = authorDao;
        this.inputOutputService = inputOutputService;
        this.bookDao = bookDao;
    }

    @Override
    public void showAuthorBooks(String authorName) {
        Author author = authorDao.getByName(authorName);
        for (Book book : author.getBooks()) {
            inputOutputService.out(book.toString());
        }
    }

    @Override
    public void addAuthor(String authorName) {
        authorDao.save(new Author(authorName));
    }

    @Override
    public void showAllAuthors() {
        List<Author> authors = authorDao.getAll();
        for (Author author : authors) {
            inputOutputService.out(author.getName());
        }
    }

    @Override
    public void addBookToAuthor(String authorName, String bookName) {
        Author author = authorDao.getByName(authorName);
        Book book = bookDao.getByName(bookName);
        if (author != null && book != null) {
            authorDao.addBookToAuthor(author, book);
        }
    }
}