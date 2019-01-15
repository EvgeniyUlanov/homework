package ru.otus.shell;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.BookDao;
import ru.otus.models.Book;
import ru.otus.services.InputOutputService;

import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class BookShellCommandsTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private Shell shell;
    @Autowired
    private InputOutputService inputOutputService;
    private PrintStream printStream;

    @Before
    public void initMock() {
        printStream = mock(PrintStream.class);
        inputOutputService.setOutput(printStream);
    }

    @Test
    public void showBookNameTest() {
        shell.evaluate(() -> "show-book-name testBook1");
        verify(printStream).println("Book{name='testBook1', genre=Drama}");
    }

    @Test
    public void showBooksTest() {
        shell.evaluate(() -> "show-books");
        verify(printStream).println("Book{name='testBook1', genre=Drama}");
        verify(printStream).println("Book{name='testBook2', genre=Comedy}");
        verify(printStream).println("Book{name='testBook3', genre=Comedy}");
    }

    @Test
    public void showBookGenreTest() {
        shell.evaluate(() -> "show-book-genre Comedy");
        verify(printStream).println("Book{name='testBook2', genre=Comedy}");
        verify(printStream).println("Book{name='testBook3', genre=Comedy}");
    }

    @Test
    public void addBookTest() {
        shell.evaluate(() -> "add-book testBook4 Comedy testAuthor");
        Book book = bookDao.getByName("testBook4");
        assertThat(book, Is.is(Matchers.notNullValue()));
    }
}
