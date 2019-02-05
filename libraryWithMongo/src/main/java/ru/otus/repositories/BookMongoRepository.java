package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.otus.models.Book;

import java.util.List;
import java.util.Optional;

@Service
public interface BookMongoRepository extends MongoRepository<Book, String> {

    List<Book> findAll();

    Optional<Book> findByName(String bookName);

    List<Book> findByAuthorsContains(String authorName);

    List<Book> findByGenreName(String genre);
}
