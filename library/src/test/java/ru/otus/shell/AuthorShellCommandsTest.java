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
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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
public class AuthorShellCommandsTest {

    @Autowired
    private Shell shell;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllAuthors() {
        entityManager.persistAndFlush(new Author("testAuthor"));
        List<String> response = (List<String>) shell.evaluate(() -> "show-all-authors");
        assertThat(response, Matchers.contains("testAuthor"));
    }

    @Test
    public void addAuthorTest() {
        shell.evaluate(() -> "add-author newAuthor");
        Author author = authorDao.getByName("newAuthor");
        assertThat(author, notNullValue());
    }

    @Test
    public void addAuthorToBookTest() {
        Author author = entityManager.persistFlushFind(new Author("testAuthor"));
        Genre genre = entityManager.persistFlushFind(new Genre("Drama"));
        entityManager.persistFlushFind(new Book(genre, "testBook3"));
        Book book = bookDao.getByAuthor(author)
                .stream().filter(e -> e.getName().equals("testBook3"))
                .findFirst()
                .orElse(null);
        assertThat(book, nullValue());
        shell.evaluate(() -> "add-book-to-author testAuthor testBook3");
        author = authorDao.getByName("testAuthor");
        book = bookDao.getByAuthor(author)
                .stream()
                .filter(e -> e.getName().equals("testBook3"))
                .findFirst()
                .orElse(new Book(new Genre("Drama"), "someBook"));
        assertThat(book.getName(), is("testBook3"));
    }
}