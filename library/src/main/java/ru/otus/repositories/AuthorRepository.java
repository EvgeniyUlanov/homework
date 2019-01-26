package ru.otus.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.models.Author;

import java.util.List;

@Repository
@Profile("springData")
public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();

    Author findByName(String name);
}
