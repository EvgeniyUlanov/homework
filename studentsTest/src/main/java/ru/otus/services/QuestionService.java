package ru.otus.services;

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
}