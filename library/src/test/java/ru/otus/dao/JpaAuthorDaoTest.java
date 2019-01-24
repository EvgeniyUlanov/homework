package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JpaAuthorDao;
import ru.otus.models.Author;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaAuthorDao.class)
public class JpaAuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddAuthorMethod() {
        Author author = new Author("newAuthor");
        authorDao.save(author);

        Author expected = entityManager.find(Author.class, author.getId());

        assertThat(expected.getName(), is("newAuthor"));
    }

    @Test
    public void testGetByNameMethod() {
        Author author = new Author("someAuthor");
        entityManager.persistAndFlush(author);

        Author expected = authorDao.getByName("someAuthor");

        assertThat(expected, is(notNullValue()));
    }

    @Test
    public void testGetByIdMethod() {
        Author author = new Author("someAuthor");
        entityManager.persistAndFlush(author);

        Author expected = authorDao.getById(author.getId());
        assertThat(expected.getName(), is("someAuthor"));
    }

    @Test
    public void testDeleteMethod() {
        Author author = new Author("newAuthor");
        author = entityManager.persistFlushFind(author);
        Long id = author.getId();

        assertThat(author, is(notNullValue()));

        authorDao.delete(author.getId());
        Author expected = entityManager.find(Author.class, id);

        assertThat(expected, is(nullValue()));
    }

    @Test
    @SuppressWarnings("JpaQlInspection")
    public void testGetAllMethod() {
        Author testAuthor1 = entityManager.persistAndFlush(new Author("testAuthor1"));
        Author testAuthor2 = entityManager.persistFlushFind(new Author("testAuthor2"));

        List<Author> authorList = authorDao.getAll();
        assertThat(authorList, containsInAnyOrder(testAuthor1, testAuthor2));
    }

    @Test
    public void testUpdateMethod() {
        Author author = new Author("newAuthor");
        entityManager.persistAndFlush(author);
        entityManager.detach(author);

        author.setName("updatedName");

        Author expected = entityManager.find(Author.class, author.getId());
        assertThat(expected.getName(), is("newAuthor"));

        authorDao.update(author);
        expected = entityManager.find(Author.class, author.getId());
        assertThat(expected.getName(), is("updatedName"));
    }
}
