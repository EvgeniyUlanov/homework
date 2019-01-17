package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;
import ru.otus.services.GenreService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<String> showAllGenres() {
        List<Genre> genreList = genreDao.getAll();
        List<String> genreNameList = new ArrayList<>();
        for (Genre genre : genreList) {
            genreNameList.add(genre.getName());
        }
        return genreNameList;
    }

    @Override
    public void addGenre(String genreName) {
        genreDao.addGenre(genreName);
    }
}