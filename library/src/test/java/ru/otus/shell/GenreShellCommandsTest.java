package ru.otus.shell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Profile({"jdbc", "jpa"})
public class GenreShellCommandsTest {

    @Autowired
    private Shell shell;
    @Autowired
    private GenreDao genreDao;

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllGenres() {
        List<String> response = (List<String>)  shell.evaluate(() -> "show-all-genres");
        assertThat(response, containsInAnyOrder("Drama", "Comedy", "Poem"));
    }

    @Test
    public void testAddGenre() {
        shell.evaluate(() -> "add-genre newGenre");
        Genre genre = genreDao.getByName("newGenre");
        assertThat(genre, notNullValue());
        genreDao.deleteGenre(genre.getId());
    }
}
