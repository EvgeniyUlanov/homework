package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.services.AuthorStringService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorStringServiceImpl implements AuthorStringService {

    private AuthorDao authorDao;
    private BookDao bookDao;

    public AuthorStringServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public List<String> showAuthorBooks(String authorName) {
        Author author = authorDao.getByName(authorName);
        List<String> bookAsStringList = new ArrayList<>();
        for (Book book : author.getBooks()) {
            bookAsStringList.add(book.toString());
        }
        return bookAsStringList;
    }

    @Override
    public void addAuthor(String authorName) {
        authorDao.save(new Author(authorName));
    }

    @Override
    public List<String> showAllAuthors() {
        List<Author> authors = authorDao.getAll();
        List<String> authorNameList = new ArrayList<>();
        for (Author author : authors) {
            authorNameList.add(author.getName());
        }
        return authorNameList;
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