package ru.otus;

import ru.otus.models.*;
import ru.otus.services.InputOutputService;
import ru.otus.services.QuestionService;
import ru.otus.services.StudentService;

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
                String givenAnswer = inputOutputService.ask(question.getQuestion());
                Answer answer = new Answer(question, givenAnswer);
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
}
