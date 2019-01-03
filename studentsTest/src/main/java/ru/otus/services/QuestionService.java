package ru.otus.services;

import ru.otus.dao.QuestionDao;
import ru.otus.models.Question;

import java.util.List;

/**
 * interface serve to interaction with QuestionDao.
 */
public interface QuestionService {

    /**
     * receive all question.
     * @return list of questions.
     */
    List<Question> getAll();

    String getCurrentTestName();
}
