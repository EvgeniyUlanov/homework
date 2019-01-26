package ru.otus.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.models.Genre;

import java.util.List;

@Repository
@Profile("springData")
public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    Genre findByName(String genre);
}
