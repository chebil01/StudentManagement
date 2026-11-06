
package controller;

import DAO.StudentDAO;
import DAO.StudentDAOIMP;
import DBconnection.DatabaseConnexion;
import model.Student;
import view.MainFrame;
import view.StudentForm;
import view.StudentTable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentController {

    private StudentForm studentForm;
    private StudentTable studentTable;
    private MainFrame view;

    private StudentDAO studentDAO;

    public StudentController(MainFrame view) {

        this.view = view;

        studentForm = view.getStudentfrom();
        studentTable = view.getStudenttable();

        try {
            Connection cn = DatabaseConnexion.getConnection();
            studentDAO = new StudentDAOIMP(cn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Database connection error: " + e.getMessage());
            return;
        }

        initializeEvents();
        ReaddbStudents();
    }

    private void initializeEvents() {

        studentForm.getAddButton().addActionListener(e -> addStudent());

        studentForm.getUpdateButton().addActionListener(e -> updateStudent());

        studentForm.getDeleteButton().addActionListener(e -> deleteStudent());

        studentForm.getClearButton().addActionListener(e -> clearform());

        studentTable.getTable().getSelectionModel()
                .addListSelectionListener(e -> loadselected());
    }

    // ADD STUDENT
    private void addStudent() {

        String name = studentForm.getNameField().getText();
        String ageText = studentForm.getAgeField().getText();
        String gender = studentForm.getGender();
        String city = studentForm.getCity();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please enter a name and age");
            return;
        }

        if (gender == null || gender.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please select a gender");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Please enter a valid age");
            return;
        }

        Student student = new Student(0, name, age, gender, city);

        studentDAO.addStudent(student);

        if (student.getId() == 0) {
            JOptionPane.showMessageDialog(view,
                    "Error adding student");
            return;
        }

        studentTable.addStudent(
                student.getId(), name, age, gender, city);

        studentForm.clear();

        JOptionPane.showMessageDialog(view,
                "Student added successfully");
    }

    // UPDATE STUDENT
    private void updateStudent() {

        int selectedRow = studentTable.getTable().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view,
                    "Please select a student");
            return;
        }

        String name = studentForm.getNameField().getText();
        String ageText = studentForm.getAgeField().getText();
        String gender = studentForm.getGender();
        String city = studentForm.getCity();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please enter a name and age");
            return;
        }

        if (gender == null || gender.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please select a gender");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Please enter a valid age");
            return;
        }

        int id = Integer.parseInt(
                studentTable.getModel()
                        .getValueAt(selectedRow, 0).toString());

        Student student = new Student(id, name, age, gender, city);

        studentDAO.updateStudent(student);

        studentTable.updateStudent(
                selectedRow, name, age, gender, city);

        studentForm.clear();
        studentTable.getTable().clearSelection();

        JOptionPane.showMessageDialog(view,
                "Student updated successfully");
    }

    // DELETE STUDENT
    private void deleteStudent() {

        int selectedRow = studentTable.getTable().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view,
                    "Please select a student");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to delete this student?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(
                studentTable.getModel()
                        .getValueAt(selectedRow, 0).toString());

        studentDAO.deleteStudent(id);

        studentTable.deleteStudent(selectedRow);

        studentForm.clear();
        studentTable.getTable().clearSelection();

        JOptionPane.showMessageDialog(view,
                "Student deleted successfully");
    }

    // CLEAR FORM
    private void clearform() {

        studentForm.clear();
        studentTable.getTable().clearSelection();
    }

    // LOAD SELECTED STUDENT
    public void loadselected() {

        int selectedRow = studentTable.getTable().getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        String name = String.valueOf(
                studentTable.getModel().getValueAt(selectedRow, 1));

        String age = String.valueOf(
                studentTable.getModel().getValueAt(selectedRow, 2));

        String gender = String.valueOf(
                studentTable.getModel().getValueAt(selectedRow, 3));

        String city = String.valueOf(
                studentTable.getModel().getValueAt(selectedRow, 4));

        studentForm.setFormData(name, age, gender, city);
    }

    // READ STUDENTS FROM DATABASE
    public void ReaddbStudents() {

        studentTable.getModel().setRowCount(0);

        List<Student> students = studentDAO.getStudents();

        for (Student student : students) {

            studentTable.getModel().addRow(
                    new Object[]{
                            student.getId(),
                            student.getName(),
                            student.getAge(),
                            student.getGender(),
                            student.getCity()
                    }
            );
        }
    }
}
