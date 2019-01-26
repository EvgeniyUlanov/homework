package ru.otus.shell;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.BookDao;
import ru.otus.models.Book;
import ru.otus.models.Comment;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Profile({"jdbc", "jpa"})
public class BookShellCommandsTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private Shell shell;

    @Test
    public void showBookNameTest() {
        String response = (String) shell.evaluate(() -> "show-book-by-name testBook1");
        assertThat(response, is("Book{name='testBook1', genre=Drama}"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showBooksTest() {
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
        shell.evaluate(() -> "add-book testBook4 Comedy testAuthor");
        Book book = bookDao.getByName("testBook4");
        assertThat(book, is(Matchers.notNullValue()));
        bookDao.delete(book.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void showAuthorBooksTest() {
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
        shell.evaluate(() -> "add-comment-to-book testBook1 testComment");
        Book testBook = bookDao.getByName("testBook1");
        Comment comment = testBook.getComments().get(0);

        assertThat(comment.getComment(), is("testComment"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllBookComments() {
        shell.evaluate(() -> "add-comment-to-book testBook2 testComment1");
        shell.evaluate(() -> "add-comment-to-book testBook2 testComment2");
        List<String> commentList = (List<String>) shell.evaluate(() -> "show-book-comments testBook2");
        assertThat(commentList, Matchers.containsInAnyOrder("testComment1", "testComment2"));
    }
}