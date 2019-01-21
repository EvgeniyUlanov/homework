package ru.otus.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.models.Author;
import ru.otus.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("JpaQlInspection")
@Transactional
@Repository
@Profile("jpa")
public class JpaBookDao implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book getById(long id) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query = entityManager.createQuery(
                "select b from Book b where b.name = :name",
                Book.class
        );
        query.setParameter("name", name);
        Book book = query.getSingleResult();
        System.out.println(book.getAuthors());
        return book;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        TypedQuery<Book> query = entityManager.createQuery(
                "select b from Book b where b.genre.name = :genre",
                Book.class
        );
        query.setParameter("genre", genre);
        return query.getResultList();
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        return getAll().stream().filter(e -> e.getAuthors().contains(author)).collect(Collectors.toList());
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(long id) {
        Book book = getById(id);
        entityManager.remove(book);
    }
}
