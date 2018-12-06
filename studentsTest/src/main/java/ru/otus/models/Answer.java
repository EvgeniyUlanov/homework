package ru.otus.models;

public class Answer {

    private Question question;
    private String givenAnswer;

    public Answer(Question question, String givenAnswer) {
        this.question = question;
        this.givenAnswer = givenAnswer;
    }

    public boolean isRight() {
        return question.getRightAnswer().equalsIgnoreCase(givenAnswer);
    }
}
