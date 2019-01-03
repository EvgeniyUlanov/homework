package ru.otus.dao;

import ru.otus.models.Question;

import java.util.List;

/**
 * interface for CRUD operations with questions.
 */
public interface QuestionDao {

    /**
     * method return all questions form source.
     * @return all questions.
     */
    List<Question> getAll();

    Question getById(int id);

    String getTestName();

    void refresh();
}
