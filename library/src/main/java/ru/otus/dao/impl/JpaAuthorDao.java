package ru.otus.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorDao;
import ru.otus.models.Author;
import ru.otus.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Transactional
@Repository
@Profile("jpa")
public class JpaAuthorDao implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Author author) {
        entityManager.persist(author);
    }

    @Override
    public Author getById(long id) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.id = :id", Author.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void update(Author author) {
        entityManager.merge(author);
    }

    @Override
    public void delete(long id) {
        Author author = getById(id);
        entityManager.remove(author);
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void addBookToAuthor(Author author, Book book) {
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
            entityManager.merge(book);
        }
    }
}
