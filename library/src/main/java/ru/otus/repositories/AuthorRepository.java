package ru.otus.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.models.Author;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("springData")
public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();

    Optional<Author> findByName(String name);
}
