package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.models.Book;
import ru.otus.repositories.BookMongoRepository;
import ru.otus.services.BookService;

import java.util.List;

@Service
public class BookServiceWithMongo implements BookService {

    private BookMongoRepository bookRepository;

    public BookServiceWithMongo(BookMongoRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookRepository.findById(1L).orElse(null);
    }

    @Override
    public Book getBookById(Long id) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return null;
    }

    @Override
    public void addBook(String bookName, String genre, String authorName) {

    }

    @Override
    public List<Book> getBookByAuthor(String authorName) {
        return null;
    }

    @Override
    public void addCommentToBook(String bookName, String comment) {

    }

    @Override
    public List<String> getCommentsByBook(String bookName) {
        return null;
    }
}
