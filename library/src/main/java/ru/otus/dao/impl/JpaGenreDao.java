package ru.otus.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Profile("jpa")
public class JpaGenreDao implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Genre getGenreById(long id) {
        return null;
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g", Genre.class);
        return null;
    }

    @Override
    public void addGenre(Genre genre) {

    }

    @Override
    public void deleteGenre(long id) {

    }

    @Override
    public List<Genre> getAll() {
        return null;
    }
}
