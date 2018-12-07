package ru.otus.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConsoleInputOutputServiceTest {

    private PrintStream printStream;
    private InputStream inputStream;

    @Before
    public void initMock() {
        printStream = mock(PrintStream.class);
        inputStream = mock(InputStream.class);
    }

    @Test
    public void testAskMethod() {
        String mockInput = "answer";
        inputStream = new ByteArrayInputStream(mockInput.getBytes());
        ConsoleInputOutputService inputOutputService = new ConsoleInputOutputService(inputStream, printStream);
        String answer = inputOutputService.ask("question");
        verify(printStream).println("question");
        assertThat(answer, is(mockInput));
    }

    @Test
    public void testMethodOut() {
        String testString = "testString";
        ConsoleInputOutputService inputOutputService = new ConsoleInputOutputService(inputStream, printStream);
        inputOutputService.out(testString);
        verify(printStream).println(testString);
    }
}
