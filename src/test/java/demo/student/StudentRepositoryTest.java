package demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepositoryTest;

    @AfterEach
    void tearDown() {
        studentRepositoryTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExist() {

        //given
        String email = "ayo@gmail.com";
        Student student = new Student(
                "Ayo",
                email,
                Gender.MALE
        );
        studentRepositoryTest.save(student);

        //when
        Boolean expected = studentRepositoryTest.selectExistsEmail(email);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {

        //given
        String email = "ayo@gmail.com";

        //when
        Boolean expected = studentRepositoryTest.selectExistsEmail(email);

        //then
        assertThat(expected).isFalse();
    }
}