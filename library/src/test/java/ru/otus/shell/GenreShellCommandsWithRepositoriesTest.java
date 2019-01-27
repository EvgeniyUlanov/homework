package ru.otus.shell;

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
import ru.otus.models.Genre;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;

@SuppressWarnings("JpaQlInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ActiveProfiles("springData")
public class GenreShellCommandsWithRepositoriesTest {

    @Autowired
    private Shell shell;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllGenres() {
        entityManager.persistAndFlush(new Genre("drama"));
        entityManager.persistAndFlush(new Genre("comedy"));
        entityManager.persistAndFlush(new Genre("poem"));
        List<String> response = (List<String>)  shell.evaluate(() -> "show-all-genres");
        assertThat(response, containsInAnyOrder("drama", "comedy", "poem"));
    }

    @Test
    public void testAddGenre() {
        shell.evaluate(() -> "add-genre newGenre");
        TypedQuery<Genre> query = entityManager.getEntityManager().createQuery(
                "select g from Genre g where g.name = 'newGenre'",
                Genre.class
        );
        Genre genre = query.getSingleResult();
        assertThat(genre, notNullValue());
    }
}
