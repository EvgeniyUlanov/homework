package ru.otus.models;

import java.util.List;

public class Answer {

    private Question question;
    private List<String> givenAnswer;

    public Answer(Question question, List<String> givenAnswer) {
        this.question = question;
        this.givenAnswer = givenAnswer;
    }

    public boolean isRight() {
        boolean result = true;
        List<String> rightAnswer = question.getRightAnswer();
        if (rightAnswer.size() == givenAnswer.size()) {
            for (String answer : givenAnswer) {
                if (rightAnswer.stream().noneMatch(e -> e.equalsIgnoreCase(answer))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
