package ru.otus.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.exeptions.EntityNotFoundException;
import ru.otus.models.Author;
import ru.otus.models.Book;
import ru.otus.repositories.AuthorRepository;
import ru.otus.repositories.BookRepository;
import ru.otus.services.AuthorService;

import java.util.List;

@Service
@Profile("springData")
public class AuthorServiceWithRepository implements AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public AuthorServiceWithRepository(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addAuthor(String authorName) {
        authorRepository.save(new Author(authorName));
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("author not found"));
    }

    @Override
    public void addBookToAuthor(String authorName, String bookName) {
        Book book = bookRepository
                .findByName(bookName)
                .orElseThrow(() -> new EntityNotFoundException("book not found"));
        Author author = authorRepository
                .findByName(authorName)
                .orElseThrow(() -> new EntityNotFoundException("author not found"));
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
            bookRepository.save(book);
        }
    }
}
