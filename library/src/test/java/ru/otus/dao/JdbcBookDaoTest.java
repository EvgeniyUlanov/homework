package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JdbcAuthorDao;
import ru.otus.dao.impl.JdbcBookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({JdbcBookDao.class, JdbcAuthorDao.class})
@Profile("jdbc")
public class JdbcBookDaoTest {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private AuthorDao authorDao;

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
