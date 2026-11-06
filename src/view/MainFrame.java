package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;

import main.main;

public class MainFrame extends JFrame  {
	 private StudentForm studentfrom;
	 private StudentTable studenttable;


	public StudentForm getStudentfrom() {
		return studentfrom;
	}

	public void setStudentfrom(StudentForm studentfrom) {
		this.studentfrom = studentfrom;
	}

	public StudentTable getStudenttable() {
		return studenttable;
	}

	public void setStudenttable(StudentTable studenttable) {
		this.studenttable = studenttable;
	}

	public MainFrame() {
	 
	    setTitle("Student Management System");
	    setSize(900, 600);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	   setLayout(new BorderLayout(10,10));
	   JLabel title = new JLabel("Student Management System",SwingConstants.CENTER);
	   title.setFont(new Font("Arial", Font.BOLD, 24));
	   add(title,BorderLayout.NORTH);
	   
	   
	   studentfrom = new StudentForm();
	   JPanel formContainer = new JPanel(
			   new BorderLayout());

	   formContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	   formContainer.add(studentfrom,BorderLayout.CENTER);
	   formContainer.setBorder(BorderFactory.createTitledBorder("Student form"));
	   add(formContainer,BorderLayout.WEST);

	   studenttable = new StudentTable();
	   JPanel tableContainer = new JPanel(new BorderLayout());
	   tableContainer.setBorder(BorderFactory.createTitledBorder("Students"));
	   tableContainer.add(studenttable,BorderLayout.CENTER);
	   add(tableContainer,BorderLayout.CENTER);


		   
   }
   
}


