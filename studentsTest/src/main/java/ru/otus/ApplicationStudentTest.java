package ru.otus;

import org.springframework.stereotype.Service;
import ru.otus.models.*;
import ru.otus.services.InputOutputService;
import ru.otus.services.MessageService;
import ru.otus.services.QuestionService;
import ru.otus.services.StudentService;

import java.util.Arrays;
import java.util.List;

@Service
public class ApplicationStudentTest {

    private InputOutputService inputOutputService;
    private QuestionService questionService;
    private StudentService studentService;
    private MessageService messageService;

    public ApplicationStudentTest(
            InputOutputService inputOutputService,
            QuestionService questionService,
            StudentService studentService,
            MessageService messageService) {
        this.inputOutputService = inputOutputService;
        this.questionService = questionService;
        this.studentService = studentService;
        this.messageService = messageService;
    }

    public void start() {
        String testName = questionService.getCurrentTestName();
        inputOutputService.out(getMessage("test.entrance") + " " + testName);
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
        return messageService.getMessage(code);
    }
}
