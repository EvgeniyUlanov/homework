package ru.otus.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.impl.JdbcAuthorDao;
import ru.otus.dao.impl.JdbcBookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({JdbcBookDao.class, JdbcAuthorDao.class})
@ActiveProfiles("jdbc")
public class JdbcBookDaoTest {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private AuthorDao authorDao;

    @BeforeEach
    public void setUp() {
        jdbcOperations.update("INSERT INTO genres (genre_name) VALUES ('Poem')", new HashMap<>());
        jdbcOperations.update("INSERT INTO genres (genre_name) VALUES ('Drama')", new HashMap<>());
    }

    @AfterEach
    public void end() {
        jdbcOperations.update("DELETE FROM books", new HashMap<>());
        jdbcOperations.update("DELETE FROM genres", new HashMap<>());
        jdbcOperations.update("DELETE FROM authors", new HashMap<>());
    }

    @Test
    public void testSaveAndGetByNameGetByIdDeleteMethods() {
        BookDao bookDao = new JdbcBookDao(jdbcOperations, authorDao);
        Book book = new Book(new Genre("Drama"),"new book");
        bookDao.save(book);

        Book expected = bookDao.getByName("new book");
        assertThat(expected.getName(), is("new book"));

        expected = bookDao.getById(book.getId());
        assertThat(expected.getId(), is(book.getId()));

        bookDao.delete(book.getId());
        assertThat(bookDao.getById(book.getId()), is(nullValue()));
    }

    @Test
    public void testGetAllBooksMethod() {
        jdbcOperations.update(
                "INSERT INTO books (book_name, genre_id) " +
                        "VALUES ('testBook1', (SELECT g.genre_id FROM genres AS g WHERE genre_name = 'Drama'))",
                new HashMap<>()
        );
        jdbcOperations.update(
                "INSERT INTO books (book_name, genre_id) " +
                        "VALUES ('testBook2', (SELECT g.genre_id FROM genres AS g WHERE genre_name = 'Drama'))",
                new HashMap<>()
        );
        jdbcOperations.update(
                "INSERT INTO books (book_name, genre_id)" +
                        " VALUES ('testBook3', (SELECT g.genre_id FROM genres AS g WHERE genre_name = 'Drama'))",
                new HashMap<>()
        );
        BookDao bookDao = new JdbcBookDao(jdbcOperations, authorDao);
        Book testBook1 = bookDao.getByName("testBook1");
        Book testBook2 = bookDao.getByName("testBook2");
        Book testBook3 = bookDao.getByName("testBook3");
        List<Book> bookList = bookDao.getAll();
        assertThat(bookList, containsInAnyOrder(testBook1, testBook2, testBook3));
    }

    @Test
    public void testGetByGenreMethod() {
        BookDao bookDao = new JdbcBookDao(jdbcOperations, authorDao);
        Genre genre = new Genre("Poem");
        Book testBook5 = new Book(genre, "testBook5");
        Book testBook6 = new Book(genre, "testBook6");
        bookDao.save(testBook5);
        bookDao.save(testBook6);
        List<Book> bookList = bookDao.getByGenre("Poem");
        assertThat(bookList, containsInAnyOrder(testBook5, testBook6));
    }

    @Test
    public void testGetByAuthorMethod() {
        Author testAuthor = new Author("testAuthor");
        authorDao.save(testAuthor);
        BookDao bookDao = new JdbcBookDao(jdbcOperations, authorDao);
        Author author = authorDao.getByName("testAuthor");
        List<Book> bookList = bookDao.getByAuthor(author);
        for (Book book : bookList) {
            assertThat(book.getAuthors(), containsInAnyOrder(author));
        }
    }

    @Test
    public void testUpdateMethod() {
        BookDao bookDao = new JdbcBookDao(jdbcOperations, authorDao);
        Book book = new Book(new Genre("Drama"), "someBook");
        bookDao.save(book);
        book = bookDao.getById(book.getId());
        assertThat(book.getName(), is("someBook"));
        book.setName("newBookName");
        bookDao.update(book);
        assertThat(bookDao.getById(book.getId()).getName(), is("newBookName"));
    }
}
