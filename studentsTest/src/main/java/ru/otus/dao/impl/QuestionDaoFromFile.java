package ru.otus.dao.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.models.Question;
import ru.otus.utils.FileNameGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionDaoFromFile implements QuestionDao {

    private final static String LINE_SEPARATOR = "\", *\"";
    private final static String ANSWER_SEPARATOR = ", *";
    private List<Question> questions;
    private String testName;
    private FileNameGenerator fileNameGenerator;

    public QuestionDaoFromFile(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
        questions = createFromFile(fileNameGenerator.generateFileName());
    }

    @Override
    public List<Question> getAll() {
        return questions;
    }

    @Override
    public Question getById(int id) {
        Question result = null;
        if (questions != null) {
            for (Question question : questions) {
                if (question.getId() == id) {
                    result = question;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public String getTestName() {
        return testName;
    }

    @Override
    public void refresh() {
        questions = createFromFile(fileNameGenerator.generateFileName());
    }

    private List<Question> createFromFile(String filename) {
        List<Question> result = new ArrayList<>();
        String line;
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename))
                     )) {
            line = br.readLine();
            if (line != null && line.contains("Test name:")) {
                this.testName = line.replaceFirst("Test name:", "").trim();
                while ((line = br.readLine()) != null) {
                    String[] rowStrings = line.split(LINE_SEPARATOR);
                    if (rowStrings.length >= 3) {
                        result.add(prepareQuestion(rowStrings));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Question prepareQuestion(String[] rowStrings) {
        String[] trimmedStrings = new String[rowStrings.length];
        for (int i = 0; i < rowStrings.length; i++) {
            trimmedStrings[i] = rowStrings[i].replace("\"", "");
        }
        Integer id = Integer.parseInt(trimmedStrings[0]);
        List<String> answers = Arrays.asList(trimmedStrings[2].split(ANSWER_SEPARATOR));
        for (int i = 0; i < answers.size(); i++) {
            answers.set(i, answers.get(i).trim());
        }
        return new Question(id, trimmedStrings[1], answers);
    }
}
