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
import ru.otus.models.Comment;
import ru.otus.models.Genre;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
public class BookShellCommandsWithRepositoriesTest {

    @Autowired
    private Shell shell;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void showBookNameTest() {
        Genre genre = entityManager.persistFlushFind(new Genre("someGenre"));
        entityManager.persistAndFlush(new Book(genre, "someBook"));

        String response = (String) shell.evaluate(() -> "show-book-by-name someBook");
        assertThat(response, is("Book{name='someBook', genre=someGenre}"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showBooksTest() {
        Genre drama = entityManager.persistFlushFind(new Genre("drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("comedy"));
        entityManager.persistAndFlush(new Book(drama, "testBook1"));
        entityManager.persistAndFlush(new Book(comedy, "testBook2"));
        entityManager.persistAndFlush(new Book(comedy, "testBook3"));

        List<String> response = (List<String>) shell.evaluate(() -> "show-all-books");
        assertThat(
                response,
                Matchers.containsInAnyOrder(
                        "Book{name='testBook1', genre=drama}",
                        "Book{name='testBook2', genre=comedy}",
                        "Book{name='testBook3', genre=comedy}")
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showBookGenreTest() {
        Genre drama = entityManager.persistFlushFind(new Genre("drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("comedy"));
        entityManager.persistAndFlush(new Book(drama, "testBook1"));
        entityManager.persistAndFlush(new Book(comedy, "testBook2"));
        entityManager.persistAndFlush(new Book(comedy, "testBook3"));

        List<String> response = (List<String>) shell.evaluate(() -> "show-books-by-genre comedy");
        assertThat(
                response,
                Matchers.containsInAnyOrder(
                        "Book{name='testBook2', genre=comedy}",
                        "Book{name='testBook3', genre=comedy}")
        );
    }

    @Test
    public void addBookTest() {
        entityManager.persistAndFlush(new Genre("comedy"));

        shell.evaluate(() -> "add-book testBook4 comedy testAuthor");
        TypedQuery<Book> query = entityManager.getEntityManager().createQuery(
                "select b from Book b where b.name = 'testBook4'",
                Book.class
        );
        Book book = query.getSingleResult();
        assertThat(book.getName(), is("testBook4"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showAuthorBooksTest() {
        Genre genre = entityManager.persistFlushFind(new Genre("someGenre"));
        Author author = entityManager.persistFlushFind(new Author("testAuthor"));
        Book testBook1 = new Book(genre, "testBook1");
        Book testBook2 = new Book(genre, "testBook2");
        testBook1.getAuthors().add(author);
        testBook2.getAuthors().add(author);
        entityManager.persistAndFlush(testBook1);
        entityManager.persistAndFlush(testBook2);

        List<String> response = (List<String>) shell.evaluate(() -> "show-books-by-author testAuthor");
        assertThat(
                response,
                Matchers.containsInAnyOrder(
                        "Book{name='testBook1', genre=someGenre}",
                        "Book{name='testBook2', genre=someGenre}")
        );
    }

    @Test
    public void testAddCommentToBook() {
        Genre genre = entityManager.persistFlushFind(new Genre("someGenre"));
        Book testBook = entityManager.persistFlushFind(new Book(genre, "testBook"));

        shell.evaluate(() -> "add-comment-to-book testBook testComment");
        testBook = entityManager.find(Book.class, testBook.getId());
        Comment comment = testBook.getComments().get(0);

        assertThat(comment.getComment(), is("testComment"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllBookComments() {
        Genre genre = entityManager.persistFlushFind(new Genre("someGenre"));
        Comment testComment1 = new Comment("testComment1");
        Comment testComment2 = new Comment("testComment2");
        Book book = new Book(genre, "testBook");
        book = entityManager.persistFlushFind(book);
        book.getComments().add(testComment1);
        book.getComments().add(testComment2);
        entityManager.persistAndFlush(book);

        List<String> commentList = (List<String>) shell.evaluate(() -> "show-book-comments testBook");
        assertThat(commentList, Matchers.containsInAnyOrder("testComment1", "testComment2"));
    }
}
