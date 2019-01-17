package ru.otus.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

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
        params.put("name", author.getName());
        jdbcOperations.update("insert into authors (author_name) values (:name)", params);
    }

    @Override
    public Author getById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        Author author = jdbcOperations.query(
                "SELECT id, author_name FROM authors where id = :id",
                params,
                new AuthorMapper()
        ).stream().findFirst().get();
        author.setBooks(getAuthorBooks(author));
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = jdbcOperations.query("select * from authors", new AuthorMapper());
        authors.forEach(e -> e.setBooks(getAuthorBooks(e)));
        return authors;
    }

    @Override
    public void update(Author author) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("author_name", author.getName());
        jdbcOperations.update("UPDATE authors AS a SET a.author_name = :author_name WHERE id = :id", params);
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
        Author author = jdbcOperations.query(
                "SELECT id, author_name FROM authors WHERE author_name = :name",
                params,
                new AuthorMapper()
        ).stream().findFirst().get();
        author.setBooks(getAuthorBooks(author));
        return author;
    }

    @Override
    public void addBookToAuthor(Author author, Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", author.getId());
        params.put("book_id", book.getId());
        jdbcOperations.update("INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)", params);
    }

    @Override
    public List<Author> getByBook(Book book) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("book_id", book.getId());
        List<Author> authorList = jdbcOperations.query(
                "SELECT a.author_name, a.id FROM authors AS a " +
                        "INNER JOIN authors_books ab ON a.id = ab.author_id " +
                        "WHERE ab.book_id = :book_id",
                params,
                new AuthorMapper()
        );
        authorList.forEach(e -> e.setBooks(getAuthorBooks(e)));
        return authorList;
    }

    private List<Book> getAuthorBooks(Author author) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", author.getId());
        return jdbcOperations.query(
                "SELECT b.id, g.genre_name, b.genre_id, b.book_name " +
                        "FROM books b " +
                        "INNER JOIN genres AS g ON b.genre_id = g.id " +
                        "INNER JOIN authors_books AS ab ON ab.book_id = b.id " +
                        "WHERE ab.author_id = :author_id",
                params,
                (rs, rowNum) -> {
                    Genre genre = new Genre(rs.getString("genre_name"));
                    genre.setId(rs.getLong("genre_id"));
                    Book book = new Book(genre, rs.getString("book_name"));
                    book.setId(rs.getLong("id"));
                    return book;
                });
    }

    private class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(rs.getString("author_name"));
            author.setId(rs.getLong("id"));
            return author;
        }
    }
}
