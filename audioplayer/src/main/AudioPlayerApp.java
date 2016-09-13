package main;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.user.UserHandler;
import model.FileHandler;
import view.AudioPlayerImpl;
import view.login.LoginGUI;

public class AudioPlayerApp {

	public static void main(String[] args){
		
		FileHandler.makeDir();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new LoginGUI().initializeGUI();
			}
		});
	}
}
