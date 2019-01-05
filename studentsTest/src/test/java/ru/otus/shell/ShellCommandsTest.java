package ru.otus.shell;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.models.Student;
import ru.otus.models.TestResult;
import ru.otus.services.InputOutputService;
import ru.otus.services.StudentService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class ShellCommandsTest {

    @Autowired
    private Shell shell;
    @Autowired
    private StudentService studentService;
    @Autowired
    private InputOutputService inputOutputService;
    private PrintStream printStream;

    @Before
    public void initMock() {
        printStream = mock(PrintStream.class);
        inputOutputService.setOutput(printStream);
    }

    @Test
    public void shellCommandAuthTest() {
        shell.evaluate(() -> "auth ivan ivanov");
        verify(printStream).println("student authorize successfully");
        assertThat(studentService.getCurrentStudent().getFirstName(), is("ivan"));
    }

    @Test
    public void shellCommandShowCurrentStudentTest() {
        studentService.setCurrentStudent(new Student("ivan", "ivanov"));
        shell.evaluate(() -> "show-current-student");
        verify(printStream).println("Student - ivan ivanov");
    }

    @Test
    public void shellCommandStartTest() {
        shell.evaluate(() -> "auth ivan ivanov");
        String answers = "answer1\nanswer2, answer3";
        InputStream inputStream = new ByteArrayInputStream(answers.getBytes());
        inputOutputService.setInput(inputStream);
        shell.evaluate(() -> "start");
        List<TestResult> testResults = studentService.getCurrentStudent().getTestResults();
        assertThat(testResults.size(), Matchers.is(1));
        assertThat(testResults.get(0).getName(), equalTo("test name"));
    }
}