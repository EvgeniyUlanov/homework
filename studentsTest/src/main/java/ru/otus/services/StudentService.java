package ru.otus.services;

import ru.otus.models.Student;

public interface StudentService {

    Student findByNameOrCreate(String firstName, String lastName);

    void setCurrentStudent(Student student);

    Student getCurrentStudent();
}
