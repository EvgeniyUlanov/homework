package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JpaGenreDao;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaGenreDao.class)
public class JpaGenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    public void testAddGetByIdGetByNameMethods() {
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
        Genre genre = new Genre("newGenre");
        genreDao.addGenre(genre);

        genreDao.deleteGenre(genre.getId());
        Genre expected = genreDao.getGenreById(genre.getId());
        assertThat(expected, is(nullValue()));
    }

    @Test
    public void testGetAllMethod() {
        Genre drama = genreDao.getByName("Drama");
        Genre comedy = genreDao.getByName("Comedy");
        Genre poem = genreDao.getByName("Poem");
        List<Genre> genreList = genreDao.getAll();
        assertThat(genreList, containsInAnyOrder(drama, comedy, poem));
    }
}
