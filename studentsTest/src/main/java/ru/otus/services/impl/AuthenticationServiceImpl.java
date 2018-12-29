package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.models.Student;
import ru.otus.services.AuthenticationService;
import ru.otus.services.StudentService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private StudentService studentService;

    public AuthenticationServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void authorize(String firstName, String lastName) {
        Student student = studentService.findByNameOrCreate(firstName, lastName);
        studentService.setCurrentStudent(student);
    }
}
