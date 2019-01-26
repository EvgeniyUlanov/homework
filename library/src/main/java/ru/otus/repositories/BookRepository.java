package ru.otus.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.models.Book;

import java.util.List;

@Repository
@Profile("springData")
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByName(String bookName);

    List<Book> findAll();

    @Query("select b from Book b join b.genre g where g.name = :genreName")
    List<Book> findByGenreName(@Param("genreName") String genre);

    @Query("select b from Book b join b.authors a where a.name = :authorName")
    List<Book> findByAuthorName(@Param("authorName") String authorName);
}
