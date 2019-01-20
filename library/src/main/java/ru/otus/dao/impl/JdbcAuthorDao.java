package ru.otus.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.models.Author;
import ru.otus.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcAuthorDao implements AuthorDao {

    private NamedParameterJdbcOperations jdbcOperations;

    public JdbcAuthorDao(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void save(Author author) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("author_name", author.getName());
        Long authorId = jdbcOperations.queryForObject(
                "INSERT INTO authors (author_name) VALUES (:author_name) ON CONFLICT DO NOTHING RETURNING author_id",
                params,
                (rs, rowNum) -> rs.getLong("author_id")
        );
        author.setId(authorId);
    }

    @Override
    public Author getById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcOperations.queryForObject(
                "SELECT author_id, author_name FROM authors WHERE author_id = :id",
                params,
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("SELECT * FROM authors", new AuthorMapper());
    }

    @Override
    public void update(Author author) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("author_name", author.getName());
        jdbcOperations.update("UPDATE authors AS a SET a.author_name = :author_name WHERE author_id = :id", params);
    }

    @Override
    public void delete(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcOperations.update("DELETE FROM authors WHERE author_id = :id", params);
    }

    @Override
    public Author getByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_name", name);
        return jdbcOperations.queryForObject(
                "SELECT author_id, author_name FROM authors WHERE author_name = :author_name",
                params,
                new AuthorMapper()
        );
    }

    @Override
    public void addBookToAuthor(Author author, Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", author.getId());
        params.put("book_id", book.getId());
        jdbcOperations.update(
                "INSERT INTO authors_books (author_id, book_id) " +
                        "VALUES (:author_id, :book_id) " +
                        "ON CONFLICT DO NOTHING ",
                params
        );
    }

    private class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(rs.getString("author_name"));
            author.setId(rs.getLong("author_id"));
            return author;
        }
    }
}