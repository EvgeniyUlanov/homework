package ru.otus.services.impl;

import org.junit.Test;
import ru.otus.dao.QuestionDao;
import ru.otus.models.Question;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceImplTest {

    @Test
    public void testMethodGetAll() {
        QuestionDao questionDao = mock(QuestionDao.class);
        Question question = new Question(1, "question", "answer");
        List<Question> questions = Arrays.asList(question);
        when(questionDao.getAll()).thenReturn(questions);
        QuestionServiceImpl questionService = new QuestionServiceImpl(questionDao);
        assertThat(questionService.getAll(), is(questions));
    }
}
