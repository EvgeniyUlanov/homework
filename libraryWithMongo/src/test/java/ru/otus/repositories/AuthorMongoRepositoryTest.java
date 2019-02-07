package ru.otus.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.models.Author;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class AuthorMongoRepositoryTest {

    @Autowired
    private AuthorMongoRepository authorMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUpCollection() {
        mongoTemplate.dropCollection("authors");
    }

    @Test
    public void testAddAuthor() {
        Author author = new Author("test author");

        authorMongoRepository.save(author);

        assertThat(author.getId(), is(notNullValue()));
    }

    @Test
    public void testFindAllAuthors() {
        Author first = new Author("first");
        Author second = new Author("second");
        mongoTemplate.insert(first);
        mongoTemplate.insert(second);

        List<Author> authors = authorMongoRepository.findAll();

        assertThat(authors, containsInAnyOrder(first, second));
    }

    @Test
    public void testFindAuthorByName() {
        Author author = new Author("test author");
        mongoTemplate.insert(author);

        Author expected = authorMongoRepository.findByName("test author").orElse(null);

        assertThat(expected, is(author));
    }

    @Test
    public void testDeleteAuthor() {
        Author author = new Author("test author");
        mongoTemplate.insert(author);

        assertThat(mongoTemplate.findById(author.getId(), Author.class), is(notNullValue()));

        authorMongoRepository.delete(author);

        mongoTemplate.findById(author.getId(), Author.class);

        assertThat(mongoTemplate.findById(author.getId(), Author.class), is(nullValue()));
    }
}
