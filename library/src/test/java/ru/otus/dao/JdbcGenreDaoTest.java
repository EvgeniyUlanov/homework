package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.impl.JdbcGenreDao;
import ru.otus.models.Genre;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({JdbcGenreDao.class})
@ActiveProfiles("jdbc")
public class JdbcGenreDaoTest {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    @Test
    public void testAddGetByIdGetByNameMethods() {
        GenreDao genreDao = new JdbcGenreDao(jdbcOperations);
        Genre genre = new Genre("newGenre");
        genreDao.addGenre(genre);

        Genre foundedByName = genreDao.getByName("newGenre");
        assertThat(foundedByName, is(genre));

        Genre foundedById = genreDao.getGenreById(genre.getId());
        assertThat(foundedById, is(genre));

        genreDao.deleteGenre(genre.getId());
    }

    @Test
    public void testDeleteMethod() {
        GenreDao genreDao = new JdbcGenreDao(jdbcOperations);
        Genre genre = new Genre("newGenre");
        genreDao.addGenre(genre);

        genreDao.deleteGenre(genre.getId());
        assertThrows(EmptyResultDataAccessException.class, () -> {
            genreDao.getGenreById(genre.getId());
        });
    }

    @Test
    public void testGetAllMethod() {
        jdbcOperations.update("INSERT INTO genres (genre_name) VALUES ('Drama')", new HashMap<>());
        jdbcOperations.update("INSERT INTO genres (genre_name) VALUES ('Comedy')", new HashMap<>());
        jdbcOperations.update("INSERT INTO genres (genre_name) VALUES ('Poem')", new HashMap<>());
        GenreDao genreDao = new JdbcGenreDao(jdbcOperations);
        Genre drama = genreDao.getByName("Drama");
        Genre comedy = genreDao.getByName("Comedy");
        Genre poem = genreDao.getByName("Poem");
        List<Genre> genreList = genreDao.getAll();
        assertThat(genreList, containsInAnyOrder(drama, comedy, poem));
    }
}
