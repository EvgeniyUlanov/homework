package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;
import ru.otus.services.GenreService;
import ru.otus.services.InputOutputService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;
    private InputOutputService inputOutputService;

    public GenreServiceImpl(GenreDao genreDao, InputOutputService inputOutputService) {
        this.genreDao = genreDao;
        this.inputOutputService = inputOutputService;
    }

    @Override
    public void showAllGenres() {
        List<Genre> genreList = genreDao.getAll();
        for (Genre genre : genreList) {
            inputOutputService.out(genre.getName());
        }
    }

    @Override
    public void addGenre(String genreName) {
        genreDao.addGenre(genreName);
    }
}
