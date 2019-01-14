package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.AuthorService;

@ShellComponent
@ShellCommandGroup("authors")
public class AuthorShellCommands {

    private AuthorService authorService;

    public AuthorShellCommands(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod("shows all authors")
    public void showAllAuthors() {

    }

    @ShellMethod("shows author's books")
    public void showAuthorBooks(@ShellOption String authorName) {
        authorService.showAuthorBooks(authorName);
    }

    @ShellMethod("add new author")
    public void addAuthor(@ShellOption String authorName) {
        authorService.addAuthor(authorName);
    }
}
