package DAO;

import DBconnection.DatabaseConnexion;
import model.Student;

import java.util.List;

public interface StudentDAO {

    void addStudent(Student s);

    void updateStudent(Student s);

    void deleteStudent(int id);

    List<Student> getStudents();

    Student findStudentById(int id);
}
