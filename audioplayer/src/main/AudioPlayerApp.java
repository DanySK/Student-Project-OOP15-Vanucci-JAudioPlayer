package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.user.LoginControllerImpl;
import model.FileHandler;

/**
 * This is the main class, it launches the login Controller creating, in case it's necessary, the
 * main directory for the app's datas
 * @author Francesco
 *
 */
public class AudioPlayerApp {

	private static final String USERS_FILE = "users.txt";
	
	public static void main(String[] args){
		
		if(!new File(FileHandler.getMainDir()).exists()){
			try {
				FileHandler.makeMainDir();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String usersPath = FileHandler.getMainDir()+USERS_FILE;
		if(!new File(usersPath).exists()){
			try {
				Files.copy(AudioPlayerApp.class.getResourceAsStream("/users/users.txt"), Paths.get(usersPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new LoginControllerImpl().initializeView();
			}
		});
	}
}
