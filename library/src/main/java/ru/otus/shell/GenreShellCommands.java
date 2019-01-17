package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.GenreService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("genres")
public class GenreShellCommands {

    private GenreService genreService;

    public GenreShellCommands(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod("shows all genres that exist in library")
    public List<String> showGenres() {
        return genreService.showAllGenres();
    }

    @ShellMethod("add new genre with name genreName - 'add-genre genreName'")
    public void addGenre(@ShellOption String genreName) {
        genreService.addGenre(genreName);
    }
}
