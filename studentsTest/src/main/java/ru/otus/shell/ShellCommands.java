package ru.otus.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.ApplicationStudentTest;
import ru.otus.services.AuthenticationService;
import ru.otus.services.InputOutputService;
import ru.otus.services.MessageService;

@ShellComponent
public class ShellCommands {

    private ApplicationStudentTest application;
    private AuthenticationService authenticationService;
    private MessageService messageService;
    private InputOutputService inputOutputService;

    public ShellCommands(
            ApplicationStudentTest application,
            AuthenticationService authenticationService,
            MessageService messageService,
            InputOutputService inputOutputService) {
        this.application = application;
        this.authenticationService = authenticationService;
        this.messageService = messageService;
        this.inputOutputService = inputOutputService;
    }

    @ShellMethod("start application")
    public void start() {
        if (authenticationService.getCurrentStudent() != null) {
            application.start();
        } else {
            showMessage("shell.authorize.not");
        }
    }

    @ShellMethod("student authentication, use 'auth firstName lastName'")
    public void auth(@ShellOption String firstName, @ShellOption String lastName) {
        authenticationService.authorize(firstName, lastName);
        showMessage("shell.authorize.success");
    }

    private void showMessage(String code) {
        inputOutputService.out(messageService.getMessage(code));
    }
}
