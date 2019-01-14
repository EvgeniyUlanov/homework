package ru.otus.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcAuthorDao implements AuthorDao {

    private NamedParameterJdbcOperations jdbcOperations;
    private BookDao bookDao;

    public JdbcAuthorDao(NamedParameterJdbcOperations jdbcOperations, BookDao bookDao) {
        this.jdbcOperations = jdbcOperations;
        this.bookDao = bookDao;
    }

    @Override
    public void save(Author author) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("name", author.getName());
        jdbcOperations.update("insert into authors (author_name) values (:name)", params);
    }

    @Override
    public Author getById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcOperations.query(
                "SELECT id, author_name FROM authors where id = :id",
                params,
                new AuthorMapper()
        ).stream().findFirst().get();
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = jdbcOperations.query("select * from authors", new AuthorMapper());
        authors.forEach(e -> e.setBooks(bookDao.getByAuthor(e)));
        return authors;
    }

    @Override
    public void update(Author author) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("author_name", author.getName());
        jdbcOperations.update("UPDATE authors AS a SET a.author_name = :author_name WHERE id = :id",params);
    }

    @Override
    public void delete(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcOperations.update("DELETE FROM authors where id = :id", params);
    }

    @Override
    public Author getByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        return jdbcOperations.query("SELECT ", params, new AuthorMapper()).stream().findFirst().get();
    }

    private class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(rs.getString("author_name"));
            author.setId(rs.getLong("id"));
            author.setBooks(bookDao.getByAuthor(author));
            return author;
        }
    }
}
