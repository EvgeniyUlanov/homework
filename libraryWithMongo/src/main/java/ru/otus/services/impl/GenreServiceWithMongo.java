package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.models.Genre;
import ru.otus.repositories.GenreMongoRepository;
import ru.otus.services.GenreService;

import java.util.List;

@Service
public class GenreServiceWithMongo implements GenreService {

    private GenreMongoRepository genreRepository;

    public GenreServiceWithMongo(GenreMongoRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void addGenre(String genreName) {
        Genre genre = genreRepository.findByName(genreName).orElse(null);
        if (genre == null) {
            genreRepository.save(new Genre(genreName));
        }
    }

    @Override
    public void deleteGenre(String genreName) {
        genreRepository.findByName(genreName).ifPresent(genre -> genreRepository.delete(genre));
    }
}
