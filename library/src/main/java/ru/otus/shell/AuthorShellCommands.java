package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.AuthorStringService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("authors")
public class AuthorShellCommands {

    private AuthorStringService authorStringService;

    public AuthorShellCommands(AuthorStringService authorStringService) {
        this.authorStringService = authorStringService;
    }

    @ShellMethod("shows all authors")
    public List<String> showAllAuthors() {
        return authorStringService.showAllAuthors();
    }

    @ShellMethod("shows author's books")
    public List<String> showAuthorBooks(@ShellOption String authorName) {
        return authorStringService.showAuthorBooks(authorName);
    }

    @ShellMethod("add new author")
    public void addAuthor(@ShellOption String authorName) {
        authorStringService.addAuthor(authorName);
    }

    @ShellMethod("add book to author")
    public void addBookToAuthor(@ShellOption String authorName, @ShellOption String bookName) {
        authorStringService.addBookToAuthor(authorName, bookName);
    }
}