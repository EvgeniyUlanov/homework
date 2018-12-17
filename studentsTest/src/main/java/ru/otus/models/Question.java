package ru.otus.models;

import java.util.List;

public class Question {

    private Integer id;
    private String question;
    private List<String> rightAnswer;

    public Question(Integer id, String question, List<String> rightAnswer) {
        this.id = id;
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    public Integer getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
