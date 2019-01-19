package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.services.BookService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public String showBookByName(String bookName) {
        Book book = bookDao.getByName(bookName);
        return book != null ? book.toString() : "cannot find book with name: " + bookName;
    }

    @Override
    public String showBookById(Long id) {
        Book book = bookDao.getById(id);
        return book != null ? book.toString() : "cannot find book with id: " + id;
    }

    @Override
    public List<String> showAllBooks() {
        List<Book> bookList = bookDao.getAll();
        List<String> bookToStringList = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookToStringList.add(book.toString());
            }
        }
        return bookToStringList;
    }

    @Override
    public List<String> showBookByGenre(String genre) {
        List<Book> bookList = bookDao.getByGenre(genre);
        List<String> bookToStringList = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookToStringList.add(book.toString());
            }
        }
        return bookToStringList;
    }

    @Override
    public List<String> showBookByAuthor(String authorName) {
        Author author = authorDao.getByName(authorName);
        List<Book> bookList = bookDao.getByAuthor(author);
        List<String> bookListAsStrings = new ArrayList<>();
        for (Book book : bookList) {
            bookListAsStrings.add(book.toString());
        }
        return bookListAsStrings;
    }

    @Override
    public String addBook(String bookName, String genre, String authorName) {
        Genre foundedGenre = genreDao.getByName(genre);
        Author foundedAuthor = authorDao.getByName(authorName);
        String message = null;
        if (foundedGenre != null && foundedAuthor != null) {
            Book book = new Book(foundedGenre, bookName);
            bookDao.save(book);
            book = bookDao.getByName(bookName);
            authorDao.addBookToAuthor(foundedAuthor, book);
            message = "added the book";
        }
        return message != null ? message : "wrong genre or author name";
    }
}
