package ru.otus.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.services.InputOutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
//@Profile(value = "dev")
public class ConsoleInputOutputService implements InputOutputService {

    private Scanner scanner;
    private PrintStream printStream;

    public ConsoleInputOutputService() {
        this(System.in, System.out);
    }

    public ConsoleInputOutputService(InputStream inputStream, PrintStream printStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String ask(String question) {
        printStream.println(question);
        return scanner.nextLine();
    }

    @Override
    public void out(String message) {
        printStream.println(message);
    }

    @Override
    public void setInput(InputStream stream) {
        scanner = new Scanner(stream);
    }
}
