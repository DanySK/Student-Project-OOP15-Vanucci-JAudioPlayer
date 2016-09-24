package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.user.LoginControllerImpl;
import model.FileHandler;

public class AudioPlayerApp {

	public static void main(String[] args){
		
		new FileHandler().makeMainDir();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
//				new LoginImpl().initializeGUI();
				new LoginControllerImpl().initializeView();
			}
		});
	}
}
