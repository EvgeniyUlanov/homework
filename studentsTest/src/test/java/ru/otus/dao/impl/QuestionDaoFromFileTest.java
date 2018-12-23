package ru.otus.dao.impl;

import org.junit.Test;
import ru.otus.models.Question;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class QuestionDaoFromFileTest {

    private final static String TEST_FILE_NAME = "questionsTest.csv";
    private final static String TEST_NAME = "test name";

    @Test
    public void getAllQuestions() {
        QuestionDaoFromFile questionDao = new QuestionDaoFromFile(TEST_NAME, TEST_FILE_NAME);
        Question firstQuestion = questionDao.getById(1);
        Question secondQuestion = questionDao.getById(2);
        assertThat(firstQuestion.getQuestion(), is("question1"));
        assertThat(firstQuestion.getRightAnswer(), contains("answer1"));
        assertThat(secondQuestion.getQuestion(), is("question2"));
        assertThat(secondQuestion.getRightAnswer(), contains("answer2", "answer3"));
    }
}
