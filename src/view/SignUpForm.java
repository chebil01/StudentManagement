package view;

import javax.swing.*;
import java.awt.*;

public class SignUpForm extends JPanel {

    private JTextField nameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton signUpButton;
    private JButton goToSignInButton;

    public SignUpForm() {

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        add(new JLabel());

        signUpButton = new JButton("Sign Up");
        add(signUpButton);

        add(new JLabel("Already have an account?"));

        goToSignInButton = new JButton("Sign In");
        add(goToSignInButton);
    }

    public String getUsername() {
        return nameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JButton getGoToSignInButton() {
        return goToSignInButton;
    }

    public void clear() {
        nameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}