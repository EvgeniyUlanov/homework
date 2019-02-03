package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.otus.models.Author;

import java.util.List;

@Service
public interface AuthorMongoRepository extends MongoRepository<Author, Long> {

    List<Author> findAll();
}
