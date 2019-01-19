package ru.otus.shell;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.BookDao;
import ru.otus.models.Book;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
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
        List<String> response = (List<String>) shell.evaluate(() -> "show-book-by-genre Comedy");
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
}