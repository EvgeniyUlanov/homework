package ru.otus.shell;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.AuthorDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;
import ru.otus.services.InputOutputService;

import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class AuthorShellCommandTest {

    @Autowired
    private Shell shell;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private InputOutputService inputOutputService;
    private PrintStream printStream;

    @Before
    public void initMock() {
        printStream = mock(PrintStream.class);
        inputOutputService.setOutput(printStream);
    }

    @Test
    public void testShowAllAuthors() {
        shell.evaluate(() -> "show-all-authors");
        verify(printStream).println("Jack London");
        verify(printStream).println("Lev Tolstoy");
        verify(printStream).println("Aleksandr Pushkin");
        verify(printStream).println("Ilya Ilf");
        verify(printStream).println("Evgeniy Petrov");
    }

    @Test
    public void showAuthorBooksTest() {
        shell.evaluate(() -> "show-author-books testAuthor");
        verify(printStream).println("Book{name='testBook1', genre=Drama}");
        verify(printStream).println("Book{name='testBook2', genre=Drama}");
    }

    @Test
    public void addAuthorTest() {
        shell.evaluate(() -> "add-author newAuthor");
        Author author = authorDao.getByName("newAuthor");
        assertThat(author, is(notNullValue()));
    }

    @Test
    public void addAuthorToBookTest() {
        Author author = authorDao.getByName("testAuthor");
        Book book = author.getBooks()
                .stream().filter(e -> e.getName().equals("testBook3"))
                .findFirst()
                .orElse(null);
        assertThat(book, is(nullValue()));
        shell.evaluate(() -> "add-book-to-author testAuthor testBook3");
        author = authorDao.getByName("testAuthor");
        book = author.getBooks()
                .stream()
                .filter(e -> e.getName().equals("testBook3"))
                .findFirst()
                .orElse(new Book(new Genre("Drama"), "someBook"));
        assertThat(book.getName(), is("testBook3"));
    }
}
