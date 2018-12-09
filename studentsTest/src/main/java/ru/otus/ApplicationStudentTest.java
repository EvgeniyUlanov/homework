package ru.otus;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.models.*;
import ru.otus.services.InputOutputService;
import ru.otus.services.QuestionService;
import ru.otus.services.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ApplicationStudentTest {

    private InputOutputService inputOutputService;
    private QuestionService questionService;
    private StudentService studentService;
    private MessageSource messageSource;
    private Locale locale;

    public ApplicationStudentTest(
            InputOutputService inputOutputService,
            QuestionService questionService,
            StudentService studentService,
            MessageSource messageSource,
            Locale locale) {
        this.inputOutputService = inputOutputService;
        this.questionService = questionService;
        this.studentService = studentService;
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public void start() {
        do {
            String testName = getMessage("test.name");
            inputOutputService.out(getMessage("test.entrance")+ " " + testName);
            inputOutputService.out(getMessage("test.auth"));
            Student student = getStudent();
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
        } while (!inputOutputService.ask(getMessage("test.finish")).equalsIgnoreCase("y"));
    }

    private Student getStudent() {
        String firstName = inputOutputService.ask(getMessage("input.first.name"));
        String lastName = inputOutputService.ask(getMessage("input.last.name"));
        return studentService.findByNameOrCreate(firstName, lastName);
    }

    private List<String> getAnswersListFromString(String givenAnswer) {
        return Arrays.asList(givenAnswer.split(", *"));
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }
}
