package ru.otus.dao;

import ru.otus.models.Student;

/**
 * interface for CRUD operations with students.
 */
public interface StudentDao {

    /**
     * find user by fist name and last name. If student is not exist so create student and save him to source.
     * @param firstName - first name of students
     * @param lastName - last name of students
     * @return student
     */
    Student findByNameOrCreate(String firstName, String lastName);
}
