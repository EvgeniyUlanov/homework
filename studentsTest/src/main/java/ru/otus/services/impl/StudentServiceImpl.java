package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.StudentDao;
import ru.otus.models.Student;

@Service
public class StudentServiceImpl implements ru.otus.services.StudentService {

    private StudentDao studentDao;
    private Student currentStudent;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student findByNameOrCreate(String firstName, String lastName) {
        return studentDao.findByNameOrCreate(firstName, lastName);
    }

    @Override
    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
    }

    @Override
    public Student getCurrentStudent() {
        return currentStudent;
    }
}
