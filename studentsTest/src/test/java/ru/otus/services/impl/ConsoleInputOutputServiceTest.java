package ru.otus.services.impl;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConsoleInputOutputServiceTest {

    @Test
    public void testAskMethod() {
        String mockInput = "answer";
        InputStream inputStream = new ByteArrayInputStream(mockInput.getBytes());
        PrintStream printStream = mock(PrintStream.class);
        ConsoleInputOutputService inputOutputService = new ConsoleInputOutputService(inputStream, printStream);
        String answer = inputOutputService.ask("question");
        verify(printStream).println("question");
        assertThat(answer, is(mockInput));
    }

    @Test
    public void testMethodOut() {
        String testString = "testString";
        InputStream inputStream = mock(InputStream.class);
        PrintStream printStream = mock(PrintStream.class);
        ConsoleInputOutputService inputOutputService = new ConsoleInputOutputService(inputStream, printStream);
        inputOutputService.out(testString);
        verify(printStream).println(testString);
    }
}
