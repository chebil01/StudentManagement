package DAO;

import model.User;
import DBconnection.DatabaseConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class UserDAOIMP implements UserDAO {

    private Connection connection;

    public UserDAOIMP(Connection connection) {
        this.connection = connection;
    }

    // LOGIN
    @Override
    public User login(String name, String password) {

        String sql = "SELECT * FROM `user` WHERE name = ? AND password = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new User(
                            rs.getInt("ID"),
                            rs.getString("name"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }

    // SIGNUP
    @Override
    public boolean signup(User user) {

        String sql = "INSERT INTO `user` (`name`, `password`) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Signup error: " + e.getMessage());
        }

        return false;
    }
}