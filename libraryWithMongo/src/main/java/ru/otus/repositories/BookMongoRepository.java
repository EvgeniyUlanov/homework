package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.otus.models.Book;

@Service
public interface BookMongoRepository extends MongoRepository<Book, Long> {
}
