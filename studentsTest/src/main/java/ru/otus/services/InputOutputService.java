package ru.otus.services;

/**
 * service uses for communicate with user
 */
public interface InputOutputService {

    /**
     * method ask's a question to client and receive the answer
     * @param question - question
     * @return answer
     */
    String ask(String question);

    /**
     * method send message to client
     * @param message message.
     */
    void out(String message);
}
