package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.GenreService;
import ru.otus.services.StringService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("genres")
public class GenreShellCommands {

    private StringService stringService;
    private GenreService genreService;

    public GenreShellCommands(StringService stringService, GenreService genreService) {
        this.stringService = stringService;
        this.genreService = genreService;
    }

    @ShellMethod("shows all genres that exist in library")
    public List<String> showAllGenres() {
        return stringService.allGenresToString();
    }

    @ShellMethod("add new genre with name genreName - 'add-genre genreName'")
    public void addGenre(@ShellOption String genreName) {
        genreService.addGenre(genreName);
    }
}