package ru.otus.shell;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;

@SuppressWarnings("JpaQlInspection")
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Profile("springData")
public class AuthorShellCommandsTestWithRepositories {

    @Autowired
    private Shell shell;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllAuthors() {
        entityManager.persistAndFlush(new Author("testAuthor1"));
        entityManager.persistAndFlush(new Author("testAuthor2"));
        List<String> response = (List<String>) shell.evaluate(() -> "show-all-authors");
        assertThat(response, Matchers.containsInAnyOrder("testAuthor1", "testAuthor2"));
    }

    @Test
    public void addAuthorTest() {
        shell.evaluate(() -> "add-author newAuthor");
        TypedQuery<Author> query = entityManager.getEntityManager().createQuery(
                "select a from Author a where a.name = 'newAuthor'",
                Author.class
        );
        Author author = query.getSingleResult();
        assertThat(author, notNullValue());
    }

    @Test
    public void addAuthorToBookTest() {
        Genre genre = entityManager.persistFlushFind(new Genre("some genre"));
        Author author = entityManager.persistFlushFind(new Author("someAuthor"));
        Book book = entityManager.persistFlushFind(new Book(genre, "someBook"));
        shell.evaluate(() -> "add-book-to-author someAuthor someBook");
        book = entityManager.find(Book.class, book.getId());
        assertThat(book.getAuthors(), containsInAnyOrder(author));
    }
}
