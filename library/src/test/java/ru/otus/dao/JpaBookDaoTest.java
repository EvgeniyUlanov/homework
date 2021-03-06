package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.impl.JpaBookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(JpaBookDao.class)
@ActiveProfiles("jpa")
public class JpaBookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAndGetByIdDeleteMethods() {
        Genre genre = entityManager.find(Genre.class, 1L);
        Book book = new Book(genre, "new book");
        bookDao.save(book);

        Book expected = entityManager.find(Book.class, book.getId());

        assertThat(expected.getName(), is("new book"));
    }

    @Test
    public void testGetByNameMethod() {
        Genre genre = entityManager.find(Genre.class, 1L);
        Book book = new Book(genre, "new book");
        entityManager.persistAndFlush(book);

        Book expected = bookDao.getByName("new book");
        assertThat(expected.getName(), is("new book"));
    }

    @Test
    public void testGetByIdMethod() {
        Genre genre = entityManager.find(Genre.class, 1L);
        Book book = new Book(genre, "new book");
        entityManager.persistAndFlush(book);

        Book expected = bookDao.getById(book.getId());
        assertThat(expected.getName(), is("new book"));
    }

    @Test
    public void testDeleteMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book book = new Book(genre, "new book");
        entityManager.persistAndFlush(book);

        Book expected = entityManager.find(Book.class, book.getId());
        assertThat(expected.getName(), is("new book"));

        bookDao.delete(book.getId());

        expected = entityManager.find(Book.class, book.getId());
        assertThat(expected, is(nullValue()));
    }

    @Test
    public void testGetAllBooksMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book testBook1 = entityManager.persistFlushFind(new Book(genre, "testBook1"));
        Book testBook2 = entityManager.persistFlushFind(new Book(genre, "testBook2"));
        Book testBook3 = entityManager.persistFlushFind(new Book(genre, "testBook3"));

        List<Book> bookList = bookDao.getAll();
        assertThat(bookList, containsInAnyOrder(testBook1, testBook2, testBook3));
    }

    @Test
    public void testGetByGenreMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book testBook5 = entityManager.persistFlushFind(new Book(genre, "testBook5"));
        Book testBook6 = entityManager.persistFlushFind(new Book(genre, "testBook6"));

        List<Book> bookList = bookDao.getByGenre(genre.getName());

        assertThat(bookList, containsInAnyOrder(testBook5, testBook6));
    }

    @Test
    public void testGetByAuthorMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book someBook1 = new Book(genre, "someBook1");
        Book someBook2 = new Book(genre, "someBook2");
        Author author = entityManager.persistFlushFind(new Author("someAuthor"));
        someBook1.getAuthors().add(author);
        someBook2.getAuthors().add(author);
        entityManager.persistAndFlush(someBook1);
        entityManager.persistAndFlush(someBook2);

        List<Book> bookList = bookDao.getByAuthor(author);

        for (Book book : bookList) {
            assertThat(book.getAuthors(), containsInAnyOrder(author));
        }
    }

    @Test
    public void testUpdateMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Book book = new Book(genre, "some book");
        entityManager.persistFlushFind(book);

        book.setName("updated book");

        Book expected = entityManager.find(Book.class, book.getId());
        assertThat(expected.getName(), is("some book"));

        bookDao.update(book);

        expected = entityManager.find(Book.class, book.getId());
        assertThat(expected.getName(), is("updated book"));
    }
}