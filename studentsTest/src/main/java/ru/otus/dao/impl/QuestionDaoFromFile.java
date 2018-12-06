package ru.otus.dao.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.models.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoFromFile implements QuestionDao {

    private final static String SEPARATOR = ",";
    private List<Question> questions;

    public QuestionDaoFromFile(String filename) {
        questions = createFromFile(filename);
    }

    @Override
    public List<Question> getAll() {
        return questions;
    }

    private List<Question> createFromFile(String filename) {
        List<Question> result = new ArrayList<>();
        String line;
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename))
                     )) {
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(SEPARATOR);
                if (strings.length >= 3) {
                    Integer id = Integer.parseInt(strings[0]);
                    Question question = new Question(id, strings[1], strings[2]);
                    result.add(question);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}