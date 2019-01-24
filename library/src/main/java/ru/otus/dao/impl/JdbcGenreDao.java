package ru.otus.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
@Profile("jdbc")
public class JdbcGenreDao implements GenreDao {

    private NamedParameterJdbcOperations jdbcOperations;

    public JdbcGenreDao(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Genre getGenreById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genre_id", id);
        return jdbcOperations.queryForObject(
                "SELECT g.genre_name, g.genre_id FROM genres g WHERE g.genre_id = :genre_id",
                params,
                new GenreMapper()
        );
    }

    @Override
    public Genre getByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genre_name", name);
        return jdbcOperations.queryForObject(
                "SELECT genre_name, genre_id FROM genres WHERE genre_name = :genre_name",
                params,
                new GenreMapper()
        );
    }

    @Override
    public void addGenre(Genre genre) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genre_name", genre.getName());
        jdbcOperations.update("INSERT INTO genres (genre_name) values (:genre_name)", params);
        genre.setId(getByName(genre.getName()).getId());
    }

    @Override
    public void deleteGenre(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genre_id", id);
        jdbcOperations.update("DELETE FROM genres WHERE genre_id = :genre_id", params);
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query("SELECT genre_name, genre_id FROM genres", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre(rs.getString("genre_name"));
            genre.setId(rs.getLong("genre_id"));
            return genre;
        }
    }
}
