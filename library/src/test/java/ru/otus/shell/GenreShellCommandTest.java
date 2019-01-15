package ru.otus.shell;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;
import ru.otus.services.InputOutputService;

import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class GenreShellCommandTest {

    @Autowired
    private Shell shell;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private InputOutputService inputOutputService;
    private PrintStream printStream;

    @Before
    public void initMock() {
        printStream = mock(PrintStream.class);
        inputOutputService.setOutput(printStream);
    }

    @Test
    public void testShowAllGenres() {
        shell.evaluate(() -> "show-genres");
        verify(printStream).println("Drama");
        verify(printStream).println("Comedy");
        verify(printStream).println("Poem");
    }

    @Test
    public void testAddGenre() {
        shell.evaluate(() -> "add-genre newGenre");
        Genre genre = genreDao.getByName("newGenre");
        assertThat(genre, is(notNullValue()));
    }
}
