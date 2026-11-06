
package controller;

import DAO.StudentDAOIMP;
import DAO.UserDAO;
import DAO.UserDAOIMP;
import DBconnection.DatabaseConnexion;
import model.User;
import view.MainFrame;


import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;


public class UserController {

    private UserDAO userDAO;
    private MainFrame view;

    // Constructor
    public UserController() {
        try {
            Connection cn = DatabaseConnexion.getConnection();
            this.userDAO = new UserDAOIMP(cn);
        } catch (
                SQLException e) {
            JOptionPane.showMessageDialog(view,
                    "Database connection error: " + e.getMessage());
            return;
        }

    }


    // LOGIN
    public User login(String name, String password) {

        if (name == null || name.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return null;
        }

        return userDAO.login(name.trim(), password);
    }

    // SIGNUP
    public boolean signup(String name, String password, String confirmPassword) {

        if (name == null || name.trim().isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            return false;
        }

        User user = new User(name.trim(), password);

        return userDAO.signup(user);
    }
}
