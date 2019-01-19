package ru.otus.shell;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class AuthorShellCommandTest {

    @Autowired
    private Shell shell;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;

    @Test
    @SuppressWarnings("unchecked")
    public void testShowAllAuthors() {
        List<String> response = (List<String>) shell.evaluate(() -> "show-all-authors");
        assertThat(response, Matchers.contains("testAuthor"));
    }

    @Test
    public void addAuthorTest() {
        shell.evaluate(() -> "add-author newAuthor");
        Author author = authorDao.getByName("newAuthor");
        assertThat(author, notNullValue());
        authorDao.delete(author.getId());
    }

    @Test
    public void addAuthorToBookTest() {
        Author author = authorDao.getByName("testAuthor");
        Book book = bookDao.getByAuthor(author)
                .stream().filter(e -> e.getName().equals("testBook3"))
                .findFirst()
                .orElse(null);
        assertThat(book, nullValue());
        shell.evaluate(() -> "add-book-to-author testAuthor testBook3");
        author = authorDao.getByName("testAuthor");
        book = bookDao.getByAuthor(author)
                .stream()
                .filter(e -> e.getName().equals("testBook3"))
                .findFirst()
                .orElse(new Book(new Genre("Drama"), "someBook"));
        assertThat(book.getName(), is("testBook3"));
    }
}