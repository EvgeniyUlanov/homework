package ru.otus.dao.impl;

import org.junit.Test;
import ru.otus.models.Question;
import ru.otus.utils.FileNameGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class QuestionDaoFromFileTest {

    private final static String TEST_FILE_NAME = "questionsTest";
    private final static String TEST_FILE_LOCALE = "en";
    private final static String TEST_FILE_SUFFIX = ".csv";

    @Test
    public void getAllQuestions() {
        FileNameGenerator fileNameGenerator = new FileNameGenerator(TEST_FILE_NAME, TEST_FILE_LOCALE, TEST_FILE_SUFFIX);
        QuestionDaoFromFile questionDao = new QuestionDaoFromFile(fileNameGenerator);
        Question firstQuestion = questionDao.getById(1);
        Question secondQuestion = questionDao.getById(2);
        assertThat(firstQuestion.getQuestion(), is("question1"));
        assertThat(firstQuestion.getRightAnswer(), contains("answer1"));
        assertThat(secondQuestion.getQuestion(), is("question2"));
        assertThat(secondQuestion.getRightAnswer(), contains("answer2", "answer3"));
    }
}
