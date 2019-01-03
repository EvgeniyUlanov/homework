package ru.otus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.models.Student;
import ru.otus.models.TestResult;
import ru.otus.services.InputOutputService;
import ru.otus.services.StudentService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class ApplicationStudentTestTest {

    @Autowired
    private ApplicationStudentTest application;
    @Autowired
    private InputOutputService inputOutputService;
    @Autowired
    private StudentService studentService;

    @Test
    public void test() {
        Student student = studentService.findByNameOrCreate("ivan", "ivanov");
        studentService.setCurrentStudent(student);
        String answers = "answer1\nanswer2, answer3";
        InputStream inputStream = new ByteArrayInputStream(answers.getBytes());
        inputOutputService.setInput(inputStream);
        application.start();
        List<TestResult> testResults = student.getTestResults();
        assertThat(testResults.size(), is(1));
        assertThat(testResults.get(0).getName(), equalTo("test name"));
    }
}
