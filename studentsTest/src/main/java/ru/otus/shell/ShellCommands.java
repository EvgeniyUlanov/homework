package ru.otus.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.ApplicationStudentTest;
import ru.otus.config.ApplicationProperties;
import ru.otus.dao.impl.QuestionDaoFromFile;
import ru.otus.models.Student;
import ru.otus.services.*;
import ru.otus.utils.FileNameGenerator;

import java.util.List;
import java.util.Locale;

@ShellComponent
public class ShellCommands {

    private ApplicationStudentTest application;
    private AuthenticationService authenticationService;
    private MessageService messageService;
    private InputOutputService inputOutputService;
    private StudentService studentService;
    private List<String> availableLanguages;
    private ApplicationProperties appProp;
    private QuestionService questionService;

    public ShellCommands(
            ApplicationStudentTest application,
            AuthenticationService authenticationService,
            MessageService messageService,
            InputOutputService inputOutputService,
            StudentService studentService,
            ApplicationProperties appProp,
            QuestionService questionService) {
        this.application = application;
        this.authenticationService = authenticationService;
        this.messageService = messageService;
        this.inputOutputService = inputOutputService;
        this.studentService = studentService;
        this.appProp = appProp;
        this.availableLanguages = appProp.getAvailableLanguages();
        this.questionService = questionService;
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

    @ShellMethod("change language, use 'ru' or 'en'")
    public void setLang(@ShellOption String locale) {
        if (availableLanguages.contains(locale)) {
            messageService.setLocale(new Locale(locale));
            showMessage("shell.language");
            String questionFileName = FileNameGenerator
                    .generateFileName(appProp.getName(),locale, appProp.getSuffix());
            questionService.setQuestionDao(new QuestionDaoFromFile(questionFileName));
        } else {
            showMessage("shell.error");
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
