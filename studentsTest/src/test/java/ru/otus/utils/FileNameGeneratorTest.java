package ru.otus.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileNameGeneratorTest {

    @Test
    public void generateFileNameWithEnLocaleTest() {
        String expected = "question.txt";
        String fileName = FileNameGenerator.generateFileName("question", "en", ".txt");
        assertThat(expected, is(fileName));
    }

    @Test
    public void generateFileNameWithRuLocaleTest() {
        String expected = "question_ru.txt";
        String fileName = FileNameGenerator.generateFileName("question", "ru", ".txt");
        assertThat(expected, is(fileName));
    }
}
