package ru.otus.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.models.Genre;
import ru.otus.repositories.GenreRepository;
import ru.otus.services.GenreService;

import java.util.List;

@Service
@Profile("springData")
public class GenreServiceWithRepository implements GenreService {

    private GenreRepository genreRepository;

    public GenreServiceWithRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void addGenre(String genreName) {
        genreRepository.save(new Genre(genreName));
    }
}
