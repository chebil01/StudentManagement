package controller;

import DBconnection.DatabaseConnexion;
import model.Student;
import view.MainFrame;
import view.StudentForm;
import view.StudentTable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentController {
    private StudentForm studentForm;
    private StudentTable studentTable;
    private MainFrame view;

    public StudentController(MainFrame view) {
        this.view = view;
        studentForm = view.getStudentfrom();
        studentTable = view.getStudenttable();
        initializeEvents();
        ReaddbStudents();
    }

    private void initializeEvents() {
        studentForm.getAddButton().addActionListener(e -> addStudent());
        studentForm.getUpdateButton().addActionListener(e -> updateStudent());
        studentForm.getDeleteButton().addActionListener(e -> deleteStudent());
        studentForm.getClearButton().addActionListener(e -> clearform());
        studentTable.getTable().getSelectionModel().addListSelectionListener(e -> loadselected());
    }


    private void addStudent() {
        String name     = studentForm.getNameField().getText();
        String ageText  = studentForm.getAgeField().getText();
        String gender   = studentForm.getGender();
        String city     = studentForm.getCity();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a name and age");
            return;
        }
        if (gender == null || gender.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a gender");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid age");
            return;
        }

        String sql = "INSERT INTO students(name, age, gender, city) VALUES (?,?,?,?)";
        try (Connection cn = DatabaseConnexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, city);
            ps.executeUpdate();

            int newId = -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) newId = keys.getInt(1);
            }
            if (newId == -1) throw new SQLException("No generated id returned");


            studentTable.addStudent(newId, name, age, gender, city);
            studentForm.clear();
            JOptionPane.showMessageDialog(view, "Student added successfully");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erreur SQL : " + ex.getMessage());
        }
    }


    private void updateStudent() {
        int selectedRow = studentTable.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a student");
            return;
        }

        String name    = studentForm.getNameField().getText();
        String ageText = studentForm.getAgeField().getText();
        String gender  = studentForm.getGender();
        String city    = studentForm.getCity();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a name and age");
            return;
        }
        if (gender == null || gender.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a gender");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid age");
            return;
        }

        int id = Integer.parseInt(
                studentTable.getModel().getValueAt(selectedRow, 0).toString());

        String sql = "UPDATE students SET name=?, age=?, gender=?, city=? WHERE id=?";
        try (Connection cn = DatabaseConnexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, city);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                JOptionPane.showMessageDialog(view, "Student not found in database");
                return;
            }

            studentTable.updateStudent(selectedRow, name, age, gender, city);
            studentForm.clear();
            studentTable.getTable().clearSelection();
            JOptionPane.showMessageDialog(view, "Student updated successfully");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erreur SQL : " + ex.getMessage());
        }
    }


    private void deleteStudent() {
        int selectedRow = studentTable.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a student");
            return;
        }
        int result = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete this student?",
                "confirm", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) return;

        int id = Integer.parseInt(
                studentTable.getModel().getValueAt(selectedRow, 0).toString());

        String sql = "DELETE FROM students WHERE id=?";
        try (Connection cn = DatabaseConnexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                JOptionPane.showMessageDialog(view, "Student not found in database");
                return;
            }
            JOptionPane.showMessageDialog(view, "Student deleted");
            studentTable.deleteStudent(selectedRow);
            studentForm.clear();
            studentTable.getTable().clearSelection();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erreur SQL : " + ex.getMessage());
        }
    }


    private void clearform() {
        studentForm.clear();
        studentTable.getTable().clearSelection();
    }


    public void loadselected() {
        int selectedRow = studentTable.getTable().getSelectedRow();
        if (selectedRow == -1) return;

        String name   = String.valueOf(studentTable.getModel().getValueAt(selectedRow, 1));
        String age    = String.valueOf(studentTable.getModel().getValueAt(selectedRow, 2));
        String gender = String.valueOf(studentTable.getModel().getValueAt(selectedRow, 3));
        String city   = String.valueOf(studentTable.getModel().getValueAt(selectedRow, 4));

        studentForm.setFormData(name, age, gender, city);
    }


    public void ReaddbStudents() {
        String sql = "SELECT id, name, age, gender, city FROM students ORDER BY id";
        try (Connection cn = DatabaseConnexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            studentTable.getModel().setRowCount(0);

            while (rs.next()) {
                int id        = rs.getInt("id");
                String name   = rs.getString("name");
                int age       = rs.getInt("age");
                String gender = rs.getString("gender");
                String city   = rs.getString("city");
                if (city == null) city = "";
                studentTable.getModel().addRow(
                        new Object[]{ id, name, age, gender, city });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erreur SQL : " + ex.getMessage());
        }
    }
}