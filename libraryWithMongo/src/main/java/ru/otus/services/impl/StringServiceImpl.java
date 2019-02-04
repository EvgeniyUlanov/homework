package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.services.AuthorService;
import ru.otus.services.BookService;
import ru.otus.services.GenreService;
import ru.otus.services.StringService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StringServiceImpl implements StringService {

    private BookService bookService;
    private AuthorService authorService;
    private GenreService genreService;

    public StringServiceImpl(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public List<String> allBooksToString() {
        List<Book> bookList = bookService.getAllBooks();
        List<String> bookListAsStrings = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookListAsStrings.add(book.toString());
            }
        }
        return bookListAsStrings;
    }

    @Override
    public List<String> bookByGenreToString(String genre) {
        List<Book> bookList = bookService.getBookByGenre(genre);
        List<String> bookListAsStrings = new ArrayList<>();
        if (bookList != null) {
            for (Book book : bookList) {
                bookListAsStrings.add(book.toString());
            }
        }
        return bookListAsStrings;
    }

    @Override
    public List<String> bookByAuthorToString(String authorName) {
        List<String> bookListAsStrings = new ArrayList<>();
        List<Book> bookList = bookService.getBookByAuthor(authorName);
        for (Book book : bookList) {
            bookListAsStrings.add(book.toString());
        }
        return bookListAsStrings;
    }

    @Override
    public String bookByNameToString(String bookName) {
        return bookService.getBookByName(bookName).toString();
    }

    @Override
    public List<String> allAuthorsToStringList() {
        List<Author> authors = authorService.getAll();
        List<String> authorNames = new ArrayList<>();
        for (Author author : authors) {
            authorNames.add(author.getName());
        }
        return authorNames;
    }

    @Override
    public List<String> allGenresToString() {
        List<Genre> genres = genreService.getAllGenres();
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName());
        }
        return genreNames;
    }
}