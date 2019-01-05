package ru.otus;

import org.springframework.stereotype.Service;
import ru.otus.config.ApplicationProperties;
import ru.otus.dao.impl.QuestionDaoFromFile;
import ru.otus.models.*;
import ru.otus.services.InputOutputService;
import ru.otus.services.MessageService;
import ru.otus.services.QuestionService;
import ru.otus.services.StudentService;
import ru.otus.utils.FileNameGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ApplicationStudentTest {

    private InputOutputService inputOutputService;
    private QuestionService questionService;
    private StudentService studentService;
    private MessageService messageService;
    private ApplicationProperties appProp;
    private FileNameGenerator fileNameGenerator;

    public ApplicationStudentTest(
            InputOutputService inputOutputService,
            QuestionService questionService,
            StudentService studentService,
            MessageService messageService,
            ApplicationProperties appProp,
            FileNameGenerator fileNameGenerator) {
        this.inputOutputService = inputOutputService;
        this.questionService = questionService;
        this.studentService = studentService;
        this.messageService = messageService;
        this.appProp = appProp;
        this.fileNameGenerator = fileNameGenerator;
    }

    public void start() {
        String testName = questionService.getCurrentTestName();
        inputOutputService.out(getMessage("test.entrance") + " " + testName);
        Student student = studentService.getCurrentStudent();
        TestResult testResult = new TestResult(testName);
        student.addTestResult(testResult);
        for (Question question : questionService.getAll()) {
            List<String> givenAnswer = getAnswersListFromString(inputOutputService.ask(question.getQuestion()));
            Answer answer = new Answer(question, givenAnswer);
            if (answer.isRight()) {
                inputOutputService.out(getMessage("test.answer.correct"));
            } else {
                inputOutputService.out(getMessage("test.answer.wrong"));
            }
            testResult.registerAnswer(answer);
        }
        inputOutputService.out(getMessage("test.score"));
        inputOutputService.out(testResult.getRightAnswersCount()
                + " " + getMessage("test.score.from")
                + " " + testResult.answersCount());
    }

    public void authorize(String firstName, String lastName) {
        Student student = studentService.findByNameOrCreate(firstName, lastName);
        studentService.setCurrentStudent(student);
        inputOutputService.out(getMessage("shell.authorize.success"));
    }

    public void showResults() {
        Student student = studentService.getCurrentStudent();
        inputOutputService.out(student.toString());
        if (student.getTestResults().size() > 0) {
            for (TestResult testResult : student.getTestResults()) {
                inputOutputService.out(testResult.getName());
                inputOutputService.out(testResult.getRightAnswersCount()
                        + " " + messageService.getMessage("test.score.from")
                        + " " + testResult.answersCount());
            }
        } else {
            inputOutputService.out(getMessage("test.result.none"));
        }
    }

    public void setLang(String locale) {
        if (appProp.getAvailableLanguages().contains(locale)) {
            messageService.setLocale(new Locale(locale));
            inputOutputService.out(getMessage("shell.language"));
            fileNameGenerator.setLocale(locale);
        } else {
            inputOutputService.out(getMessage("shell.error"));
        }
    }

    private List<String> getAnswersListFromString(String givenAnswer) {
        return Arrays.asList(givenAnswer.split(", *"));
    }

    private String getMessage(String code) {
        return messageService.getMessage(code);
    }
}
