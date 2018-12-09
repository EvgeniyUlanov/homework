package ru.otus;

import ru.otus.models.*;
import ru.otus.services.InputOutputService;
import ru.otus.services.QuestionService;
import ru.otus.services.StudentService;

import java.util.Arrays;
import java.util.List;

public class ApplicationStudentTest {

    private InputOutputService inputOutputService;
    private QuestionService questionService;
    private StudentService studentService;
    private String testName;

    public ApplicationStudentTest(
            InputOutputService inputOutputService,
            QuestionService questionService,
            StudentService studentService,
            String testName) {
        this.inputOutputService = inputOutputService;
        this.questionService = questionService;
        this.studentService = studentService;
        this.testName = testName;
    }

    public void start() {
        do {
            inputOutputService.out("This is test - " + testName);
            inputOutputService.out("Please, identify your self");
            Student student = getStudent();
            TestResult testResult = new TestResult(testName);
            student.addTestResult(testResult);
            for (Question question : questionService.getAll()) {
                List<String> givenAnswer = getAnswersListFromString(inputOutputService.ask(question.getQuestion()));
                Answer answer = new Answer(question, givenAnswer);
                if (answer.isRight()) {
                    inputOutputService.out("correct");
                } else {
                    inputOutputService.out("wrong");
                }
                testResult.registerAnswer(answer);
            }
            inputOutputService.out("Your test score:");
            inputOutputService.out(testResult.getRightAnswersCount() + " from " + testResult.answersCount());
        } while (!inputOutputService.ask("Finish?y/n").equalsIgnoreCase("y"));
    }

    private Student getStudent() {
        String firstName = inputOutputService.ask("enter your first name");
        String lastName = inputOutputService.ask("enter your last name");
        return studentService.findByNameOrCreate(firstName, lastName);
    }

    private List<String> getAnswersListFromString(String givenAnswer) {
        return Arrays.asList(givenAnswer.split(", *"));
    }
}
