package ru.otus.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.BookService;
import ru.otus.services.StringService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("books")
public class BookShellCommands {

    private StringService stringService;
    private BookService bookService;

    public BookShellCommands(StringService stringService, BookService bookService) {
        this.stringService = stringService;
        this.bookService = bookService;
    }

    @ShellMethod("shows book by name")
    public String showBookByName(@ShellOption String bookName) {
        return stringService.bookByNameToString(bookName);
    }

    @ShellMethod("shows all books")
    public List<String> showAllBooks() {
        return stringService.allBooksToString();
    }

    @ShellMethod("shows books by genre")
    public List<String> showBooksByGenre(@ShellOption String genreName) {
       return stringService.bookByGenreToString(genreName);
    }

    @ShellMethod("shows books by author")
    public List<String> showBooksByAuthor(@ShellOption String authorName) {
        return stringService.bookByAuthorToString(authorName);
    }

    @ShellMethod("add book, example: add-book 'book name' genreName baseAuthorName," +
            " to add another author use command add-author-to-book")
    public void addBook(@ShellOption String bookName, @ShellOption String genre, @ShellOption String author) {
        bookService.addBook(bookName, genre, author);
    }

    @ShellMethod("add comment to book")
    public void addCommentToBook(@ShellOption String bookName, @ShellOption String comment) {
        bookService.addCommentToBook(bookName, comment);
    }

    @ShellMethod("show book's comments")
    public List<String> showBookComments(@ShellOption String bookName) {
        return bookService.getCommentsByBook(bookName);
    }

    @ShellMethod("add author to book")
    public void addAuthorToBook(String authorName, String bookName) {
        bookService.addAuthorToBook(authorName, bookName);
    }
}
