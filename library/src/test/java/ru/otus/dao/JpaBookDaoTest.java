package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JpaAuthorDao;
import ru.otus.dao.impl.JpaBookDao;
import ru.otus.dao.impl.JpaGenreDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({JpaAuthorDao.class, JpaGenreDao.class, JpaBookDao.class})
public class JpaBookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private GenreDao genreDao;

    @Test
    public void testSaveAndGetByNameGetByIdDeleteMethods() {
        Genre genre = genreDao.getByName("Drama");
        Book book = new Book(genre, "new book");
        bookDao.save(book);

        Book expected = bookDao.getByName("new book");
        System.out.println(expected);
        assertThat(expected.getName(), is("new book"));

        expected = bookDao.getById(book.getId());
        assertThat(expected.getId(), is(book.getId()));

        bookDao.delete(book.getId());
        assertThat(bookDao.getById(book.getId()), is(nullValue()));
    }

    @Test
    public void testGetAllBooksMethod() {
        Book testBook1 = bookDao.getByName("testBook1");
        Book testBook2 = bookDao.getByName("testBook2");
        Book testBook3 = bookDao.getByName("testBook3");
        List<Book> bookList = bookDao.getAll();
        assertThat(bookList, containsInAnyOrder(testBook1, testBook2, testBook3));
    }

    @Test
    public void testGetByGenreMethod() {
        Genre genre = genreDao.getByName("Poem");
        Book testBook5 = new Book(genre, "testBook5");
        Book testBook6 = new Book(genre, "testBook6");
        bookDao.save(testBook5);
        bookDao.save(testBook6);
        List<Book> bookList = bookDao.getByGenre("Poem");
        assertThat(bookList, containsInAnyOrder(testBook5, testBook6));
    }

    @Test
    public void testGetByAuthorMethod() {
        Author author = authorDao.getByName("testAuthor");
        List<Book> bookList = bookDao.getByAuthor(author);
        for (Book book : bookList) {
            assertThat(book.getAuthors(), containsInAnyOrder(author));
        }
    }

    @Test
    public void testUpdateMethod() {
        Genre genre = genreDao.getByName("Drama");
        Book book = new Book(genre, "someBook");
        bookDao.save(book);
        System.out.println(book.getId());
        book = bookDao.getById(book.getId());
        assertThat(book.getName(), is("someBook"));
        book.setName("newBookName");
        bookDao.update(book);
        assertThat(bookDao.getById(book.getId()).getName(), is("newBookName"));
    }
}