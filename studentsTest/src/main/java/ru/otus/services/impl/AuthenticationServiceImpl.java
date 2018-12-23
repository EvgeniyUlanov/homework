package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.StudentDao;
import ru.otus.models.Student;
import ru.otus.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private StudentDao studentDao;
    private Student currentStudent;

    public AuthenticationServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void authorize(String firstName, String lastName) {
        currentStudent = studentDao.findByNameOrCreate(firstName, lastName);
    }

    @Override
    public Student getCurrentStudent() {
        return currentStudent;
    }
}
