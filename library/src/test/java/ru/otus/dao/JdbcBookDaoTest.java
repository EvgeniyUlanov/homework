package ru.otus.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.impl.JdbcBookDao;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class JdbcBookDaoTest {

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testSaveAndGetByNameMethod() {
        BookDao bookDao = new JdbcBookDao(jdbcOperations);
        Book book = new Book(new Genre("Drama"),"new book");
        bookDao.save(book);
        Book expected = bookDao.getByName("new book");
        assertThat(expected.getName(), is("new book"));
    }
}
