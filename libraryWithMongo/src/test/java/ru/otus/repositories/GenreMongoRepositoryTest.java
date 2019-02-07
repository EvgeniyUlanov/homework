package ru.otus.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.models.Genre;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class GenreMongoRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GenreMongoRepository genreMongoRepository;

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection("genres");
    }

    @Test
    public void testFindAllGenres() {
        Genre firstGenre = new Genre("first genre");
        Genre secondGenre = new Genre("second genre");
        mongoTemplate.save(firstGenre);
        mongoTemplate.save(secondGenre);

        List<Genre> genres = genreMongoRepository.findAll();

        assertThat(genres, containsInAnyOrder(firstGenre, secondGenre));
    }

    @Test
    public void testFindGenreByName() {
        Genre genre = new Genre("some genre");
        mongoTemplate.save(genre);

        Genre expected = genreMongoRepository.findByName("some genre").orElse(null);

        assertThat(expected, is(notNullValue()));
    }
}
