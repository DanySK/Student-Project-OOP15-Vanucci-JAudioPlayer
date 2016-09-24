package main;

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
		
		FileHandler.makeMainDir();
		
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
