package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.otus.models.Genre;

@Service
public interface GenreMongoRepository extends MongoRepository<Genre, Long> {
}
