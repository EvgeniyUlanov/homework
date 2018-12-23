package ru.otus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "dev")
public class ConsoleRun implements CommandLineRunner {

    @Autowired
    ApplicationStudentTest test;

    @Override
    public void run(String... args) throws Exception {
        test.start();
    }
}
