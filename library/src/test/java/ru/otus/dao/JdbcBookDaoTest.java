package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JdbcBookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@JdbcTest
@Import(JdbcBookDao.class)
public class JdbcBookDaoTest {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testSaveAndGetByNameMethod() {
        BookDao bookDao = new JdbcBookDao(jdbcOperations, authorDao);
        Book book = new Book(new Genre("Drama"),"new book");
        bookDao.save(book);
        Book expected = bookDao.getByName("new book");
        assertThat(expected.getName(), is("new book"));
    }
}
