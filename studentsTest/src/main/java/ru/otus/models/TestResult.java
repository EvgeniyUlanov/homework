package ru.otus.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private String name;
    private Timestamp date;
    private List<Answer> answers = new ArrayList<>();

    public TestResult(String name) {
        this.name = name;
        date = new Timestamp(System.currentTimeMillis());
    }

    public void registerAnswer(Answer answer) {
        answers.add(answer);
    }

    public int getRightAnswersCount() {
        int count = 0;
        for (Answer answer : answers) {
            if (answer.isRight()) {
                count++;
            }
        }
        return count;
    }

    public int answersCount() {
        return answers.size();
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
