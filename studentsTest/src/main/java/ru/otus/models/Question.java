package ru.otus.models;

public class Question {

    private Integer id;
    private String question;
    private String rightAnswer;

    public Question(Integer id, String question, String rightAnswer) {
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

    public String getRightAnswer() {
        return rightAnswer;
    }
}
