package ru.otus.services.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.models.Question;
import ru.otus.services.QuestionService;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getAll() {
        return questionDao.getAll();
    }

    @Override
    public String getCurrentTestName() {
        return questionDao.getTestName();
    }
}
