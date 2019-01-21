package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.services.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public void addAuthor(String authorName) {
        Author author = new Author(authorName);
        authorDao.save(author);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Author getByName(String name) {
        return authorDao.getByName(name);
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
