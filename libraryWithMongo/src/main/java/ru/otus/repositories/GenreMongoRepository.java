package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.otus.models.Genre;

import java.util.List;
import java.util.Optional;

@Service
public interface GenreMongoRepository extends MongoRepository<Genre, Long> {

    List<Genre> findAll();

    Optional<Genre> findByName(String genreName);
}
