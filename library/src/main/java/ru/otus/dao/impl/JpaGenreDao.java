package ru.otus.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Transactional
@Repository
@Profile("jpa")
public class JpaGenreDao implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Genre getGenreById(long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void addGenre(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public void deleteGenre(long id) {
        entityManager.remove(getGenreById(id));
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
        System.out.println(genreList.size());
        return genreList;
    }
}
