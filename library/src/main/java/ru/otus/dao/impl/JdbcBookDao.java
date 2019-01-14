package ru.otus.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcBookDao implements BookDao {

    private NamedParameterJdbcOperations jdbcOperations;

    public JdbcBookDao(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
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
                params);
    }

    @Override
    public Book getById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcOperations.query("SELECT b.id, g.genre_name, b.genre_id, b.book_name FROM books AS b " +
                "INNER JOIN genres AS g ON g.id = b.genre_id " +
                "WHERE b.id = :id", params, new BookMapper()
        ).stream().findFirst().get();
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query(
                "SELECT b.id, g.genre_name, b.genre_id, b.book_name FROM books AS b " +
                "INNER JOIN genres AS g ON g.id = b.genre_id",
                new BookMapper()
        );
    }

    @Override
    public Book getByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bookName", name);
        return jdbcOperations.query(
                "SELECT b.id, g.genre_name, b.genre_id, b.book_name " +
                        "FROM books b " +
                        "INNER JOIN genres AS g ON g.id = b.genre_id " +
                        "where book_name = :bookName",
                params,
                new BookMapper()
        ).stream().findFirst().get();
    }

    @Override
    public List<Book> getByGenre(String genre) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genreName", genre);
        return jdbcOperations.query(
                "SELECT b.id, g.genre_name, b.genre_id, b.book_name " +
                        "FROM books b " +
                        "INNER JOIN genres AS g ON b.genre_id = g.id " +
                        "WHERE genre_name = :genreName",
                params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", author.getId());
        return jdbcOperations.query(
                "SELECT b.id, g.genre_name, b.genre_id, b.book_name " +
                        "FROM books b " +
                        "INNER JOIN genres AS g ON b.genre_id = g.id " +
                        "INNER JOIN authors_books AS ab ON ab.book_id = b.id " +
                        "WHERE ab.author_id = :author_id",
                params,
                new BookMapper());
    }

    @Override
    public void update(Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("book_name", book.getName());
        params.put("genre_id", book.getGenre().getId());
        jdbcOperations.update(
                "UPDATE books AS b SET b.book_name =:book_name, b.genre_id = :genre_id WHERE id = :id",
                params
        );
    }

    @Override
    public void delete(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcOperations.update("DELETE FROM books where id = :id", params);
    }

    @Override
    public void addAuthorToBook(Author foundedAuthor, Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", foundedAuthor.getId());
        params.put("book_id", book.getId());
        jdbcOperations.update("INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)", params);
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre(rs.getString("genre_name"));
            genre.setId(rs.getLong("genre_id"));
            Book book = new Book(genre, rs.getString("book_name"));
            book.setId(rs.getLong("id"));
            return book;
        }
    }
}