package view;

import javax.swing.*;
import java.awt.*;

public class SignInForm extends JPanel {

    private JTextField nameField;
    private JPasswordField passwordField;

    private JButton signInButton;
    private JButton goToSignUpButton;

    public SignInForm() {

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel());

        signInButton = new JButton("Sign In");
        add(signInButton);

        add(new JLabel("Don't have an account?"));

        goToSignUpButton = new JButton("Sign Up");
        add(goToSignUpButton);
    }

    public String getUsername() {
        return nameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JButton getGoToSignUpButton() {
        return goToSignUpButton;
    }

    public void clear() {
        nameField.setText("");
        passwordField.setText("");
    }
}