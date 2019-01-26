package ru.otus.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;
import ru.otus.services.BookService;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"jdbc", "jpa"})
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
    public Book getBookByName(String bookName) {
        return bookDao.getByName(bookName);
    }

    @Override
    public Book getBookById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return bookDao.getByGenre(genre);
    }

    @Override
    public List<Book> getBookByAuthor(String authorName) {
        Author author = authorDao.getByName(authorName);
        return bookDao.getByAuthor(author);
    }

    @Override
    public void addBook(String bookName, String genre, String authorName) {
        Genre foundedGenre = genreDao.getByName(genre);
        Author foundedAuthor;
        try {
            foundedAuthor = authorDao.getByName(authorName);
        } catch (Exception e) {
            foundedAuthor = new Author(authorName);
        }
        Book book = new Book(foundedGenre, bookName);
        book.getAuthors().add(foundedAuthor);
        bookDao.save(book);
    }

    @Override
    public void addCommentToBook(String bookName, String commentString) {
        Book book = bookDao.getByName(bookName);
        book.getComments().add(new Comment(commentString));
        bookDao.update(book);
    }

    @Override
    public List<String> getCommentsByBook(String bookName) {
        Book book = bookDao.getByName(bookName);
        List<String> commentList = new ArrayList<>();
        for (Comment comment : book.getComments()) {
            commentList.add(comment.getComment());
        }
        return commentList;
    }
}
