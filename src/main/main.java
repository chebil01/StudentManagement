package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import DBconnection.DatabaseConnexion;
import controller.StudentController;
import view.MainFrame;

import java.sql.Connection;
import java.sql.SQLException;

public class main {

	public static void main(String[] args) {	
		
		SwingUtilities.invokeLater(()->{
			
			MainFrame frame = new MainFrame();
			new StudentController(frame);
			frame.setVisible(true);
		});

		try( Connection cx= DatabaseConnexion.getConnection()) {
			if(cx!=null){
				System.out.println("Connexion réussie!!");
			}else {
				System.out.println("Connexion non réussie!!");
			}
		}catch(SQLException e){
			System.out.println("erreur in cnx!!");
			e.printStackTrace();
		}
	}
	}


