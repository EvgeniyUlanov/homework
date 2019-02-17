package ru.otus.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class BookMongoRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BookMongoRepository bookMongoRepository;

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection("books");
        mongoTemplate.dropCollection("authors");
        mongoTemplate.dropCollection("genres");
    }

    @Test
    public void testAddBook() {
        Genre genre = new Genre("some genre");
        mongoTemplate.save(genre);
        Book book = new Book(genre, "test book");

        bookMongoRepository.save(book);

        assertThat(book.getId(), is(notNullValue()));
    }

    @Test
    public void testFindAllBooks() {
        Genre genre = new Genre("some genre");
        Genre genre1 = new Genre("another genre");
        mongoTemplate.save(genre);
        mongoTemplate.save(genre1);
        Book firstBook = new Book(genre,"first book");
        Book secondBook = new Book(genre1, "second book");
        mongoTemplate.save(firstBook);
        mongoTemplate.save(secondBook);

        List<Book> books= bookMongoRepository.findAll();

        assertThat(books, containsInAnyOrder(firstBook, secondBook));
    }

    @Test
    public void testFindBookByName() {
        Genre genre = new Genre("some genre");
        mongoTemplate.save(genre);
        Book book = new Book(genre, "test book");
        mongoTemplate.save(book);

        Book expected = bookMongoRepository.findByName("test book").orElse(null);

        assertThat(expected, is(book));
    }

    @Test
    public void testFindBooksByAuthor() {
        Genre genre = new Genre("some genre");
        Author firstAuthor = new Author("first author");
        Author secondAuthor = new Author("second author");
        mongoTemplate.save(genre);
        mongoTemplate.save(firstAuthor);
        mongoTemplate.save(secondAuthor);
        Book firstBook = new Book(genre, "first book");
        firstBook.getAuthors().add(firstAuthor);
        Book secondBook = new Book(genre, "second book");
        secondBook.getAuthors().add(firstAuthor);
        Book thirdBook = new Book(genre, "third book");
        thirdBook.getAuthors().add(secondAuthor);
        mongoTemplate.save(firstBook);
        mongoTemplate.save(secondBook);
        mongoTemplate.save(thirdBook);

        List<Book> books = bookMongoRepository.findByAuthorsContains(firstAuthor);

        assertThat(books, containsInAnyOrder(firstBook, secondBook));
    }

    @Test
    public void testFindBooksByGenre() {
        Genre drama = new Genre("drama");
        Genre comedy = new Genre("comedy");
        mongoTemplate.save(drama);
        mongoTemplate.save(comedy);
        Book firstBook = new Book(drama, "first book");
        Book secondBook = new Book(drama, "second book");
        Book thirdBook = new Book(comedy, "third book");
        mongoTemplate.save(firstBook);
        mongoTemplate.save(secondBook);
        mongoTemplate.save(thirdBook);

        List<Book> books = bookMongoRepository.findByGenre(drama);

        assertThat(books, containsInAnyOrder(firstBook, secondBook));
    }
}
