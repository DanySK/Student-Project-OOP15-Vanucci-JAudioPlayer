package main;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

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

	public static void main(String[] args){
		
		if(!new File(FileHandler.getMainDir()).exists()){
			try {
				FileHandler.makeMainDir();
				String usersPath = AudioPlayerApp.class.getResource("/users/users.txt").getFile();
				usersPath = URLDecoder.decode(usersPath, "utf-8");
				Files.copy(new File(usersPath).toPath(), FileHandler.toFile("users.txt"));
			} catch (IOException e) {
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
