package DAO;

import DBconnection.DatabaseConnexion;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOIMP implements StudentDAO {

    private Connection connection;

    public StudentDAOIMP(Connection connection) {
        this.connection = connection;

    }
    @Override
    public void addStudent(Student s) {
        String sql = "INSERT INTO students(name, age, gender, city) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
        ps.setString(1, s.getName());
        ps.setInt(2, s.getAge());
        ps.setString(3, s.getGender());
        ps.setString(4, s.getCity());
        ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public void updateStudent(Student s) {
        String sql = "UPDATE students SET name=?, age=?, gender=?, city=? WHERE id=?" ;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setString(3, s.getGender());
            ps.setString(4, s.getCity());
            ps.setInt(5, s.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getStudents() {
        String sql = "SELECT id, name, age, gender, city FROM students ORDER BY id";
        List<Student> students = new ArrayList<>();
        try(Statement st= connection.createStatement();
        ResultSet rs = st.executeQuery(sql)){
        while(rs.next()){
            Student student = new Student(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("city"));
            students.add(student);
        }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public Student findStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id=?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
           ResultSet rs =  ps.executeQuery();
           if(rs.next()){
               return new Student(rs.getInt("id"),
                       rs.getString("name"),
                       rs.getInt("age"),
                       rs.getString("gender"),
                       rs.getString("city"));
           }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
