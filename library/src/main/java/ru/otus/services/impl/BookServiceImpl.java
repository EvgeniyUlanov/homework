package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.services.BookService;
import ru.otus.services.InputOutputService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    private InputOutputService inputOutputService;

    public BookServiceImpl(BookDao bookDao, InputOutputService inputOutputService, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.inputOutputService = inputOutputService;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void showBookByName(String bookName) {
        Book book = bookDao.getByName(bookName);
        if (book != null) {
            inputOutputService.out(book.toString());
        } else {
            inputOutputService.out("book with name '" + bookName + "' not found");
        }
    }

    @Override
    public void showBookById(Long id) {
        Book book = bookDao.getById(id);
        if (book != null) {
            inputOutputService.out(book.toString());
        } else {
            inputOutputService.out("book with id '" + id + "' not found");
        }
    }

    @Override
    public void showAllBooks() {
        List<Book> bookList = bookDao.getAll();
        if (bookList != null) {
            printBookList(bookList);
        } else {
            inputOutputService.out("error to receive books");
        }
    }

    @Override
    public void showBookByGenre(String genre) {
        List<Book> bookList = bookDao.getByGenre(genre);
        if (bookList != null && !bookList.isEmpty()) {
            printBookList(bookList);
        } else {
            inputOutputService.out("book with this genre is not found");
        }
    }

    @Override
    public void addBook(String bookName, String genre, String authorName) {
        Genre foundedGenre = genreDao.getByName(genre);
        Author foundedAuthor = authorDao.getByName(authorName);
        if (foundedGenre != null && foundedAuthor != null) {
            Book book = new Book(foundedGenre, bookName);
            bookDao.save(book);
            bookDao.addAuthorToBook(foundedAuthor, book);
        } else {
            inputOutputService.out("wrong genre or author");
        }
    }

    @Override
    public void addAuthorToBook(String authorName, String bookName) {
        Author author = authorDao.getByName(authorName);
        Book book = bookDao.getByName(bookName);
        if (author != null && book != null) {
            bookDao.addAuthorToBook(author, book);
        }
    }

    private void printBookList(List<Book> bookList) {
        for (Book book : bookList) {
            inputOutputService.out(book.toString());
        }
    }
}
