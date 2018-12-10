package ru.otus.dao.impl;

import org.junit.Test;
import ru.otus.models.Student;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InMemoryStudentsDaoImplTest {

    @Test
    public void testAddStudent() {
        InMemoryStudentsDaoImpl studentsDao = new InMemoryStudentsDaoImpl();
        Student student = studentsDao.findByNameOrCreate("ivan", "ivanov");
        assertThat(student.getFirstName(), is("ivan"));
    }

    @Test
    public void testGetExistedStudent() {
        InMemoryStudentsDaoImpl studentsDao = new InMemoryStudentsDaoImpl();
        Student student = studentsDao.findByNameOrCreate("ivan", "ivanov");
        Student studentExpected = studentsDao.findByNameOrCreate("ivan", "ivanov");
        assertThat(student == studentExpected, is(true));
    }
}
