package ru.otus.services.impl;

import ru.otus.dao.StudentDao;
import ru.otus.models.Student;

public class StudentServiceImpl implements ru.otus.services.StudentService {

    private StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student findByNameOrCreate(String firstName, String lastName) {
        return studentDao.findByNameOrCreate(firstName, lastName);
    }
}
