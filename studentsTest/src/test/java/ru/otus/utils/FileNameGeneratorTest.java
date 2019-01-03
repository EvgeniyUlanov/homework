package ru.otus.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileNameGeneratorTest {

    @Test
    public void generateFileNameWithEnLocaleTest() {
        FileNameGenerator fileNameGenerator = new FileNameGenerator("question", "en", ".txt");
        String expected = "question.txt";
        String fileName = fileNameGenerator.generateFileName();
        assertThat(expected, is(fileName));
    }

    @Test
    public void generateFileNameWithRuLocaleTest() {
        FileNameGenerator fileNameGenerator = new FileNameGenerator("question", "ru", ".txt");
        String expected = "question_ru.txt";
        String fileName = fileNameGenerator.generateFileName();
        assertThat(expected, is(fileName));
    }
}
