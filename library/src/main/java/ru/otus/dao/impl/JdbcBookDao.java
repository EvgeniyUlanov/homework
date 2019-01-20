package ru.otus.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcBookDao implements BookDao {

    private NamedParameterJdbcOperations jdbcOperations;
    private AuthorDao authorDao;

    public JdbcBookDao(NamedParameterJdbcOperations jdbcOperations, AuthorDao authorDao) {
        this.jdbcOperations = jdbcOperations;
        this.authorDao = authorDao;
    }

    @Override
    public void save(Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("book_name", book.getName());
        params.put("genre", book.getGenre().getName());
        jdbcOperations.update(
                "INSERT INTO books (book_name, genre_id) " +
                        "VALUES (" +
                        ":book_name, " +
                        "(SELECT genre_id FROM genres where genre_name =:genre))",
                params
        );
        book.setId(getByName(book.getName()).getId());
        for (Author author : book.getAuthors()) {
            authorDao.save(author);
            authorDao.addBookToAuthor(author, book);
        }
    }

    @Override
    public Book getById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcOperations.query(
                "SELECT b.book_id, g.genre_name, b.genre_id, b.book_name, a.author_id, a.author_name FROM books AS b " +
                        "INNER JOIN genres AS g ON g.genre_id = b.genre_id " +
                        "LEFT JOIN authors_books ab on b.book_id = ab.book_id " +
                        "LEFT JOIN authors AS a ON ab.author_id = a.author_id " +
                        "WHERE b.book_id = :id",
                params,
                new ResultSetExtractorForBook()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query(
                "SELECT b.book_id, g.genre_name, b.genre_id, b.book_name, a.author_id, a.author_name FROM books AS b " +
                        "INNER JOIN genres AS g ON g.genre_id = b.genre_id " +
                        "LEFT JOIN authors_books ab on b.book_id = ab.book_id " +
                        "LEFT JOIN authors AS a ON ab.author_id = a.author_id ",
                new ResultSetExtractorForBookList()
        );
    }

    @Override
    public Book getByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bookName", name);
        return jdbcOperations.query(
                "SELECT b.book_id, g.genre_name, b.genre_id, b.book_name, a.author_id, a.author_name FROM books AS b " +
                        "INNER JOIN genres AS g ON g.genre_id = b.genre_id " +
                        "LEFT JOIN authors_books ab on b.book_id = ab.book_id " +
                        "LEFT JOIN authors AS a ON ab.author_id = a.author_id " +
                        "WHERE b.book_name = :bookName",
                params,
                new ResultSetExtractorForBook()
        );
    }

    @Override
    public List<Book> getByGenre(String genre) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genreName", genre);
        return jdbcOperations.query(
                "SELECT b.book_id, g.genre_name, b.genre_id, b.book_name, a.author_id, a.author_name FROM books AS b " +
                        "INNER JOIN genres AS g ON g.genre_id = b.genre_id " +
                        "LEFT JOIN authors_books ab on b.book_id = ab.book_id " +
                        "LEFT JOIN authors AS a ON ab.author_id = a.author_id " +
                        "WHERE g.genre_name = :genreName",
                params,
                new ResultSetExtractorForBookList()
        );
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", author.getId());
        return jdbcOperations.query(
                "SELECT b.book_id, g.genre_name, b.genre_id, b.book_name, a.author_id, a.author_name " +
                        "FROM books b " +
                        "INNER JOIN genres AS g ON b.genre_id = g.genre_id " +
                        "INNER JOIN authors_books AS ab ON ab.book_id = b.book_id " +
                        "INNER JOIN authors AS a ON ab.author_id = a.author_id " +
                        "WHERE ab.author_id = :author_id",
                params,
                new ResultSetExtractorForBookList()
        );
    }

    @Override
    public void update(Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("book_name", book.getName());
        params.put("genre_id", book.getGenre().getId());
        jdbcOperations.update(
                "UPDATE books AS b SET b.book_name =:book_name, b.genre_id = :genre_id WHERE book_id = :id",
                params
        );
    }

    @Override
    public void delete(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcOperations.update("DELETE FROM books where book_id = :id", params);
    }

    private class ResultSetExtractorForBookList implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Book> resultBookList = new ArrayList<>();
            while (rs.next()) {
                Long bookId = rs.getLong("book_id");
                Book book = resultBookList.stream().filter(e -> e.getId() == bookId).findFirst().orElse(null);
                if (book == null) {
                    Genre genre = new Genre(rs.getString("genre_name"));
                    genre.setId(rs.getLong("genre_id"));
                    book = new Book(genre, rs.getString("book_name"));
                    book.setId(bookId);
                    resultBookList.add(book);
                }
                List<Author> authorList = book.getAuthors();
                Long authorId = rs.getLong("author_id");
                String authorName = rs.getString("author_name");
                if (authorName != null) {
                    Author author = authorList.stream().filter(e -> e.getId() == authorId).findFirst().orElse(null);
                    if (author == null) {
                        author = new Author(rs.getString("author_name"));
                        author.setId(authorId);
                        authorList.add(author);
                    }
                }
            }
            return resultBookList;
        }
    }

    private class ResultSetExtractorForBook implements ResultSetExtractor<Book> {
        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            rs.next();
            Genre genre = new Genre(rs.getString("genre_name"));
            Book book = new Book(genre, rs.getString("book_name"));
            book.setId(rs.getLong("book_id"));
            List<Author> authorList = book.getAuthors();
            do {
                Long authorId = rs.getLong("author_id");
                String authorName = rs.getString("author_name");
                if (authorName != null) {
                    Author author = authorList.stream().filter(e -> e.getId() == authorId).findFirst().orElse(null);
                    if (author == null) {
                        author = new Author(authorName);
                        author.setId(authorId);
                        authorList.add(author);
                    }
                }
            } while (rs.next());
            return book;
        }
    }
}