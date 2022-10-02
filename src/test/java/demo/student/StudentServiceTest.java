package demo.student;


import demo.student.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }


    @Test
    void canGetAllStudents() {
        //when
        studentService.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }


    @Test
    void canAddStudent() {
        //given
        String email = "ayo@gmail.com";
        Student student = new Student(
                "Ayo",
                email,
                Gender.MALE
        );
        //when
        studentService.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

//        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailsTaken() {
        //given
        String email = "ayo@gmail.com";
        Student student = new Student(
                "Ayo",
                email,
                Gender.MALE
        );

        given(studentRepository.selectExistsEmail(student.getEmail())) //can use anyString() as well
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());
    }

    @Disabled
    @Test
    void deleteStudent() {
    }
}