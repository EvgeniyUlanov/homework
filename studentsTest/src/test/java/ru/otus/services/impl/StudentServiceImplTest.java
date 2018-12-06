package ru.otus.services.impl;

import org.junit.Test;
import ru.otus.dao.StudentDao;
import ru.otus.models.Student;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    @Test
    public void methodFindByNameOrCreateTest() {
        StudentDao studentDao = mock(StudentDao.class);
        String firstName = "firstName";
        String lastName = "lastName";
        Student student = new Student(firstName, lastName);
        when(studentDao.findByNameOrCreate(firstName, lastName)).thenReturn(student);
        StudentServiceImpl studentService = new StudentServiceImpl(studentDao);
        assertThat(studentService.findByNameOrCreate(firstName, lastName), is(student));
    }
}
