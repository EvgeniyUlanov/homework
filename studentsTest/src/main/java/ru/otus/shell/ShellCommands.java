package ru.otus.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.ApplicationStudentTest;
import ru.otus.models.Student;
import ru.otus.services.AuthenticationService;
import ru.otus.services.InputOutputService;
import ru.otus.services.MessageService;
import ru.otus.services.StudentService;

@ShellComponent
public class ShellCommands {

    private ApplicationStudentTest application;
    private AuthenticationService authenticationService;
    private MessageService messageService;
    private InputOutputService inputOutputService;
    private StudentService studentService;

    public ShellCommands(
            ApplicationStudentTest application,
            AuthenticationService authenticationService,
            MessageService messageService,
            InputOutputService inputOutputService,
            StudentService studentService) {
        this.application = application;
        this.authenticationService = authenticationService;
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
        authenticationService.authorize(firstName, lastName);
        showMessage("shell.authorize.success");
    }

    @ShellMethod("shows current student")
    public void showCurrentStudent() {
        Student student = studentService.getCurrentStudent();
        if (student != null) {
            inputOutputService.out(student.toString());
        } else {
            showMessage("shell.authorize.not");
        }
    }

    private void showMessage(String code) {
        inputOutputService.out(messageService.getMessage(code));
    }


    private Availability checkAuth() {
        return studentService.getCurrentStudent() != null
                ? Availability.available()
                : Availability.unavailable(messageService.getMessage("shell.authorize.not"));
    }
}
