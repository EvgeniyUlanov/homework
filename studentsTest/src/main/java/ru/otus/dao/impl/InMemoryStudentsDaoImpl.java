package ru.otus.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.dao.StudentDao;
import ru.otus.models.Student;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryStudentsDaoImpl implements StudentDao {

    private List<Student> students = new ArrayList<>();

    @Override
    public Student findByNameOrCreate(String firstName, String lastName) {
        Student result = null;
        for (Student student : students) {
            if (student.getFirstName()
                    .equalsIgnoreCase(firstName) && student.getLastName().equalsIgnoreCase(lastName)) {
                result = student;
                break;
            }
        }
        if (result == null) {
            result = new Student(firstName, lastName);
            students.add(result);
        }
        return result;
    }
}
