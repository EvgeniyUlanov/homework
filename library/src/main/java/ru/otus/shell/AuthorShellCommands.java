package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.AuthorService;
import ru.otus.services.AuthorStringService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("authors")
public class AuthorShellCommands {

    private AuthorStringService authorStringService;
    private AuthorService authorService;

    public AuthorShellCommands(AuthorStringService authorStringService, AuthorService authorService) {
        this.authorStringService = authorStringService;
        this.authorService = authorService;
    }

    @ShellMethod("shows all authors")
    public List<String> showAllAuthors() {
        return authorStringService.showAllAuthors();
    }

    @ShellMethod("add new author")
    public void addAuthor(@ShellOption String authorName) {
        authorService.addAuthor(authorName);
    }

    @ShellMethod("add book to author")
    public void addBookToAuthor(@ShellOption String authorName, @ShellOption String bookName) {
        authorService.addBookToAuthor(authorName, bookName);
    }
}