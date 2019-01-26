package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JdbcAuthorDao;
import ru.otus.models.Author;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({JdbcAuthorDao.class})
@Profile("jdbc")
public class JdbcAuthorDaoTest {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    @Test
    public void testAddAuthorGetByNameGetByIdMethods() {
        AuthorDao authorDao = new JdbcAuthorDao(jdbcOperations);
        Author author = new Author("newAuthor");
        authorDao.save(author);

        Author foundedByName = authorDao.getByName("newAuthor");
        assertThat(foundedByName, is(author));

        Author foundedById = authorDao.getById(author.getId());
        assertThat(foundedById, is(author));

        authorDao.delete(author.getId());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteMethod() {
        AuthorDao authorDao = new JdbcAuthorDao(jdbcOperations);
        Author author = new Author("newAuthor");
        authorDao.save(author);

        Author foundedByName = authorDao.getByName("newAuthor");
        assertThat(foundedByName, is(author));

        authorDao.delete(author.getId());
        authorDao.getById(author.getId());
    }

    @Test
    public void testGetAllMethod() {
        AuthorDao authorDao = new JdbcAuthorDao(jdbcOperations);
        Author author = new Author("newAuthor");
        authorDao.save(author);
        Author testAuthor = authorDao.getByName("testAuthor");

        List<Author> authorList = authorDao.getAll();
        assertThat(authorList, containsInAnyOrder(author, testAuthor));
    }

    @Test
    public void testUpdateMethod() {
        AuthorDao authorDao = new JdbcAuthorDao(jdbcOperations);
        Author author = new Author("newAuthor");
        authorDao.save(author);

        Author expected = authorDao.getByName("newAuthor");
        assertThat(expected.getName(), is("newAuthor"));
        expected.setName("updatedName");

        authorDao.update(expected);
        assertThat(expected.getId(), is(author.getId()));
        assertThat(authorDao.getById(expected.getId()).getName(), is("updatedName"));

        authorDao.delete(author.getId());
    }
}
