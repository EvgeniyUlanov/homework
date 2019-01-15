package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.BookService;

@ShellComponent
@ShellCommandGroup("books")
public class BookShellCommands {

    private BookService bookService;

    public BookShellCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod("shows book by name")
    public void showBookName(@ShellOption String bookName) {
        bookService.showBookByName(bookName);
    }

    @ShellMethod("shows all books")
    public void showBooks() {
        bookService.showAllBooks();
    }

    @ShellMethod("shows book list by genre")
    public void showBookGenre(@ShellOption String genreName) {
        bookService.showBookByGenre(genreName);
    }

    @ShellMethod("add book, example: add-book 'book name' genreName baseAuthorName," +
            " to add another author use command add-author-to-book")
    public void addBook(@ShellOption String bookName, @ShellOption String genre, @ShellOption String author) {
        bookService.addBook(bookName, genre, author);
    }
}
