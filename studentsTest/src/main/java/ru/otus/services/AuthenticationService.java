package ru.otus.services;

import ru.otus.models.Student;

public interface AuthenticationService {

    void authorize(String firstName, String lastName);

    Student getCurrentStudent();
}
