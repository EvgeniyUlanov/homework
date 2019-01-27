package ru.otus.shell;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Comment;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ActiveProfiles("jpa")
public class BookShellCommandsTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private Shell shell;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void showBookNameTest() {
        Genre genre = entityManager.persistFlushFind(new Genre("Drama"));
        entityManager.persistAndFlush(new Book(genre, "testBook1"));
        String response = (String) shell.evaluate(() -> "show-book-by-name testBook1");
        assertThat(response, is("Book{name='testBook1', genre=Drama}"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showBooksTest() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("Comedy"));
        entityManager.persistAndFlush(new Book(drama, "testBook1"));
        entityManager.persistAndFlush(new Book(comedy, "testBook2"));
        entityManager.persistAndFlush(new Book(comedy, "testBook3"));
        List<String> response = (List<String>) shell.evaluate(() -> "show-all-books");
        assertThat(
                response,
                Matchers.containsInAnyOrder(
                        "Book{name='testBook1', genre=Drama}",
                        "Book{name='testBook2', genre=Comedy}",
                        "Book{name='testBook3', genre=Comedy}")
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showBookGenreTest() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("Comedy"));
        entityManager.persistAndFlush(new Book(drama, "testBook1"));
        entityManager.persistAndFlush(new Book(comedy, "testBook2"));
        entityManager.persistAndFlush(new Book(comedy, "testBook3"));
        List<String> response = (List<String>) shell.evaluate(() -> "show-books-by-genre Comedy");
        assertThat(
                response,
                Matchers.containsInAnyOrder(
                        "Book{name='testBook2', genre=Comedy}",
                        "Book{name='testBook3', genre=Comedy}")
        );
    }

    @Test
    public void addBookTest() {
        entityManager.persistAndFlush(new Genre("Comedy"));
        shell.evaluate(() -> "add-book testBook4 Comedy testAuthor");
        Book book = bookDao.getByName("testBook4");
        assertThat(book, is(Matchers.notNullValue()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showAuthorBooksTest() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("Comedy"));
        Book testBook1 = entityManager.persistFlushFind(new Book(drama, "testBook1"));
        Book testBook2 = entityManager.persistFlushFind(new Book(comedy, "testBook2"));
        Author author = entityManager.persistFlushFind(new Author("testAuthor"));
        testBook1.getAuthors().add(author);
        testBook2.getAuthors().add(author);
        entityManager.persistFlushFind(testBook1);
        entityManager.persistFlushFind(testBook2);
        List<String> response = (List<String>) shell.evaluate(() -> "show-books-by-author testAuthor");
        assertThat(
                response,
                Matchers.containsInAnyOrder(
                        "Book{name='testBook1', genre=Drama}",
                        "Book{name='testBook2', genre=Comedy}")
        );
    }

    @Test
    public void testAddCommentToBook() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        entityManager.persistFlushFind(new Book(drama, "testBook1"));
        shell.evaluate(() -> "add-comment-to-book testBook1 testComment");
        Book testBook = bookDao.getByName("testBook1");
        Comment comment = testBook.getComments().get(0);

        assertThat(comment.getComment(), is("testComment"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllBookComments() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        entityManager.persistFlushFind(new Book(drama, "testBook2"));
        shell.evaluate(() -> "add-comment-to-book testBook2 testComment1");
        shell.evaluate(() -> "add-comment-to-book testBook2 testComment2");
        List<String> commentList = (List<String>) shell.evaluate(() -> "show-book-comments testBook2");
        assertThat(commentList, Matchers.containsInAnyOrder("testComment1", "testComment2"));
    }
}