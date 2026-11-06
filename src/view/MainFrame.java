
package view;

import java.awt.*;
import javax.swing.*;

import controller.UserController;
import model.User;

public class MainFrame extends JFrame {

	private StudentForm studentfrom;
	private StudentTable studenttable;

	private SignUpForm signUpForm;
	private SignInForm signInForm;

	private JPanel mainPanel;
	private CardLayout cardLayout;

	private UserController userController;

	// Logged-in user welcome label
	private JLabel welcomeLabel;

	public MainFrame() {

		setTitle("Student Management System");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		userController = new UserController();

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		// SIGN IN
		signInForm = new SignInForm();
		mainPanel.add(signInForm, "SIGNIN");

		// SIGN UP
		signUpForm = new SignUpForm();
		mainPanel.add(signUpForm, "SIGNUP");

		// STUDENT MANAGEMENT
		studentfrom = new StudentForm();
		studenttable = new StudentTable();

		JPanel studentPanel = new JPanel(new BorderLayout(10, 10));

		// NORTH: TITLE + WELCOME USER
		JPanel northPanel = new JPanel(new BorderLayout());

		JLabel title = new JLabel(
				"Student Management System",
				SwingConstants.CENTER
		);

		title.setFont(new Font("Arial", Font.BOLD, 24));

		welcomeLabel = new JLabel(
				"Welcome!",
				SwingConstants.RIGHT
		);

		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));

		northPanel.add(title, BorderLayout.CENTER);
		northPanel.add(welcomeLabel, BorderLayout.SOUTH);

		northPanel.setBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
		);

		studentPanel.add(northPanel, BorderLayout.NORTH);

		// STUDENT FORM
		JPanel formContainer = new JPanel(new BorderLayout());

		formContainer.setBorder(
				BorderFactory.createTitledBorder("Student Form")
		);

		formContainer.add(studentfrom, BorderLayout.CENTER);

		studentPanel.add(formContainer, BorderLayout.WEST);

		// STUDENT TABLE
		JPanel tableContainer = new JPanel(new BorderLayout());

		tableContainer.setBorder(
				BorderFactory.createTitledBorder("Students")
		);

		tableContainer.add(studenttable, BorderLayout.CENTER);

		studentPanel.add(tableContainer, BorderLayout.CENTER);

		mainPanel.add(studentPanel, "STUDENTS");

		// NAVIGATION: SIGN IN -> SIGN UP
		signInForm.getGoToSignUpButton().addActionListener(e -> {
			cardLayout.show(mainPanel, "SIGNUP");
		});

		// NAVIGATION: SIGN UP -> SIGN IN
		signUpForm.getGoToSignInButton().addActionListener(e -> {
			cardLayout.show(mainPanel, "SIGNIN");
		});

		// LOGIN
		signInForm.getSignInButton().addActionListener(e -> {

			String name = signInForm.getUsername();
			String password = signInForm.getPassword();

			User user = userController.login(name, password);

			if (user != null) {

				// DISPLAY LOGGED-IN USER NAME
				welcomeLabel.setText("Welcome, " + user.getName());

				JOptionPane.showMessageDialog(
						this,
						"Login successful! Welcome " + user.getName()
				);

				signInForm.clear();

				cardLayout.show(mainPanel, "STUDENTS");

			} else {

				JOptionPane.showMessageDialog(
						this,
						"Invalid username or password!",
						"Login Error",
						JOptionPane.ERROR_MESSAGE
				);
			}
		});

		// SIGNUP
		signUpForm.getSignUpButton().addActionListener(e -> {

			String name = signUpForm.getUsername();
			String password = signUpForm.getPassword();
			String confirmPassword = signUpForm.getConfirmPassword();

			if (name.isEmpty() || password.isEmpty()
					|| confirmPassword.isEmpty()) {

				JOptionPane.showMessageDialog(
						this,
						"Please fill in all fields!"
				);

				return;
			}

			if (!password.equals(confirmPassword)) {

				JOptionPane.showMessageDialog(
						this,
						"Passwords do not match!"
				);

				return;
			}

			if (password.length() < 4) {

				JOptionPane.showMessageDialog(
						this,
						"Password must contain at least 4 characters!"
				);

				return;
			}

			boolean success = userController.signup(
					name,
					password,
					confirmPassword
			);

			if (success) {

				JOptionPane.showMessageDialog(
						this,
						"Account created successfully!"
				);

				signUpForm.clear();

				cardLayout.show(mainPanel, "SIGNIN");

			} else {

				JOptionPane.showMessageDialog(
						this,
						"Signup failed! Check database connection or username.",
						"Signup Error",
						JOptionPane.ERROR_MESSAGE
				);
			}
		});

		add(mainPanel);

		// START WITH SIGN IN
		cardLayout.show(mainPanel, "SIGNIN");
	}

	// GETTERS

	public StudentForm getStudentfrom() {
		return studentfrom;
	}

	public StudentTable getStudenttable() {
		return studenttable;
	}

	public SignUpForm getSignUpForm() {
		return signUpForm;
	}

	public SignInForm getSignInForm() {
		return signInForm;
	}

	// NAVIGATION METHODS

	public void showSignIn() {
		cardLayout.show(mainPanel, "SIGNIN");
	}

	public void showSignUp() {
		cardLayout.show(mainPanel, "SIGNUP");
	}

	public void showStudents() {
		cardLayout.show(mainPanel, "STUDENTS");
	}
}
