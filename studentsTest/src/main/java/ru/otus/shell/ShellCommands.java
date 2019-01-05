package ru.otus.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.ApplicationStudentTest;
import ru.otus.services.*;

@ShellComponent
public class ShellCommands {

    private ApplicationStudentTest application;
    private MessageService messageService;
    private InputOutputService inputOutputService;
    private StudentService studentService;

    public ShellCommands(
            ApplicationStudentTest application,
            MessageService messageService,
            InputOutputService inputOutputService,
            StudentService studentService) {
        this.application = application;
        this.messageService = messageService;
        this.inputOutputService = inputOutputService;
        this.studentService = studentService;
    }

    @ShellMethod("start application")
    @ShellMethodAvailability("checkAuth")
    public void start() {
        application.start();
    }

    @ShellMethod("student authentication, use 'auth firstName lastName'")
    public void auth(@ShellOption String firstName, @ShellOption String lastName) {
        application.authorize(firstName, lastName);
    }

    @ShellMethod("shows current student")
    @ShellMethodAvailability("checkAuth")
    public void showCurrentStudent() {
        inputOutputService.out(studentService.getCurrentStudent().toString());
    }

    @ShellMethod("change language, use 'ru' or 'en'")
    public void setLang(@ShellOption String locale) {
        application.setLang(locale);

    }

    @ShellMethod("show current student results")
    @ShellMethodAvailability("checkAuth")
    public void showResults() {
        application.showResults();
    }

    private Availability checkAuth() {
        return studentService.getCurrentStudent() != null
                ? Availability.available()
                : Availability.unavailable(messageService.getMessage("shell.authorize.not"));
    }
}
