package DAO;

import model.User;

public interface UserDAO {

    User login(String name, String password);

    boolean signup(User user);
}