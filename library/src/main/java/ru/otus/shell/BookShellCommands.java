package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.BookService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("books")
public class BookShellCommands {

    private BookService bookService;

    public BookShellCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod("shows book by name")
    public String showBookByName(@ShellOption String bookName) {
        return bookService.showBookByName(bookName);
    }

    @ShellMethod("shows all books")
    public List<String> showAllBooks() {
        return bookService.showAllBooks();
    }

    @ShellMethod("shows book list by genre")
    public List<String> showBookByGenre(@ShellOption String genreName) {
       return bookService.showBookByGenre(genreName);
    }

    @ShellMethod("add book, example: add-book 'book name' genreName baseAuthorName," +
            " to add another author use command add-author-to-book")
    public String addBook(@ShellOption String bookName, @ShellOption String genre, @ShellOption String author) {
        return bookService.addBook(bookName, genre, author);
    }
}
