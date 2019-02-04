package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.customExeptions.BookNotFoundException;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.repositories.AuthorMongoRepository;
import ru.otus.repositories.BookMongoRepository;
import ru.otus.repositories.GenreMongoRepository;
import ru.otus.services.BookService;

import java.util.List;

@Service
public class BookServiceWithMongo implements BookService {

    private BookMongoRepository bookRepository;
    private AuthorMongoRepository authorRepository;
    private GenreMongoRepository genreRepository;

    public BookServiceWithMongo(BookMongoRepository bookRepository,
                                AuthorMongoRepository authorRepository,
                                GenreMongoRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookRepository.findByName(bookName).orElse(null);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return bookRepository.findByGenreName(genre);
    }

    @Override
    public void addBook(String bookName, String genreName, String authorName) {
        Genre genre = genreRepository.findByName(genreName).orElse(null);
        if (genre == null) {
            genre = genreRepository.save(new Genre(genreName));
        }
        Book book = new Book(genre, bookName);
        Author author = authorRepository.findByName(authorName).orElse(null);
        if (author == null) {
            author = authorRepository.save(new Author(authorName));
        }
        book.getAuthors().add(author);
        bookRepository.insert(book);
    }

    @Override
    public List<Book> getBookByAuthor(String authorName) {
        return bookRepository.findByAuthorsContains(authorName);
    }

    @Override
    public void addCommentToBook(String bookName, String comment) {
        Book book = bookRepository.findByName(bookName).orElseThrow(() -> new BookNotFoundException("book not found"));
        book.getComments().add(comment);
        bookRepository.save(book);
    }

    @Override
    public List<String> getCommentsByBook(String bookName) {
        return bookRepository
                .findByName(bookName)
                .orElseThrow(() -> new BookNotFoundException("book not found"))
                .getComments();
    }

    @Override
    public void addAuthorToBook(String authorName, String bookName) {
        Book book = bookRepository.findByName(bookName).orElseThrow(() -> new BookNotFoundException("book not found"));
        Author author = authorRepository.findByName(authorName).orElse(null);
        if (author == null) {
            author = authorRepository.save(new Author(authorName));
        }
        book.getAuthors().add(author);
        bookRepository.save(book);
    }
}
