package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JpaAuthorDao;
import ru.otus.models.Author;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaAuthorDao.class)
public class JpaAuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testAddAuthorGetByNameGetByIdMethods() {
        Author author = new Author("newAuthor");
        authorDao.save(author);

        Author foundedByName = authorDao.getByName("newAuthor");
        assertThat(foundedByName, is(author));

        Author foundedById = authorDao.getById(author.getId());
        assertThat(foundedById, is(author));

        authorDao.delete(author.getId());
    }

    @Test
    public void testDeleteMethod() {
        Author author = new Author("newAuthor");
        authorDao.save(author);

        Author foundedByName = authorDao.getByName("newAuthor");
        assertThat(foundedByName, is(author));

        authorDao.delete(author.getId());
        Author expected = authorDao.getById(author.getId());

        assertThat(expected, is(nullValue()));
    }

    @Test
    public void testGetAllMethod() {
        Author author = new Author("newAuthor");
        authorDao.save(author);
        Author testAuthor = authorDao.getByName("testAuthor");

        List<Author> authorList = authorDao.getAll();
        assertThat(authorList, containsInAnyOrder(author, testAuthor));
    }

    @Test
    public void testUpdateMethod() {
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
