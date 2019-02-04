package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.AuthorService;
import ru.otus.services.StringService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("authors")
public class AuthorShellCommands {

    private StringService stringService;
    private AuthorService authorService;

    public AuthorShellCommands(StringService stringService, AuthorService authorService) {
        this.stringService = stringService;
        this.authorService = authorService;
    }

    @ShellMethod("shows all authors")
    public List<String> showAllAuthors() {
        return stringService.allAuthorsToStringList();
    }

    @ShellMethod("add new author")
    public void addAuthor(@ShellOption String authorName) {
        authorService.addAuthor(authorName);
    }
}