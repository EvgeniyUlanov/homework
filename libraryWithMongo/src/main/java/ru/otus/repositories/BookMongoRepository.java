package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.models.Genre;

import java.util.List;
import java.util.Optional;

@Service
public interface BookMongoRepository extends MongoRepository<Book, String> {

    List<Book> findAll();

    Optional<Book> findByName(String bookName);

    List<Book> findByAuthorsContains(Author authorName);

    List<Book> findByGenre(Genre genre);
}
