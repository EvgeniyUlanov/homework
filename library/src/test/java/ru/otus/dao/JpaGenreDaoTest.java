package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JpaGenreDao;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaGenreDao.class)
public class JpaGenreDaoTest {

    @Autowired
    private GenreDao genreDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveMethod() {
        Genre genre = new Genre("newGenre");
        genreDao.addGenre(genre);

        Genre founded = entityManager.find(Genre.class, genre.getId());
        assertThat(founded, is(genre));
    }

    @Test
    public void testGetByIdMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("new genre"));

        Genre foundedById = genreDao.getGenreById(genre.getId());
        assertThat(foundedById.getName(), is("new genre"));
    }

    @Test
    public void testGetByName() {
        entityManager.persist(new Genre("new genre"));

        Genre foundedByName = genreDao.getByName("new genre");
        assertThat(foundedByName.getName(), is("new genre"));
    }

    @Test
    public void testDeleteMethod() {
        Genre genre = entityManager.persistFlushFind(new Genre("new genre"));

        assertThat(genre, is(notNullValue()));

        genreDao.deleteGenre(genre.getId());
        Genre expected = entityManager.find(Genre.class, genre.getId());

        assertThat(expected, is(nullValue()));
    }

    @Test
    public void testGetAllMethod() {
        Genre drama = entityManager.persistFlushFind(new Genre("Drama"));
        Genre comedy = entityManager.persistFlushFind(new Genre("Comedy"));
        Genre poem = entityManager.persistFlushFind(new Genre("Poem"));

        List<Genre> genreList = genreDao.getAll();

        assertThat(genreList, containsInAnyOrder(drama, comedy, poem));
    }
}