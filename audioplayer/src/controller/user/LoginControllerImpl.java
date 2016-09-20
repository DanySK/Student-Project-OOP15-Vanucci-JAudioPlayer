package controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.FileHandlerImpl;
import model.user.User;
import model.user.UserImpl;
import view.AudioPlayerImpl;
import view.login.LoginGUI;
import view.login.LoginImpl;

public class LoginControllerImpl implements LoginController{

	private LoginGUI loginView;
	private User currentUser;
	private FileHandlerImpl fileHandler;
	
	public LoginControllerImpl(){
		this.loginView = new LoginImpl();
		this.currentUser = new UserImpl();
		this.fileHandler = new FileHandlerImpl();
		loginView.addActionListener(new LoginListener());
	}
	
	@Override
	public void initializeView(){
		this.loginView.initializeGUI();
	}
	
	@Override
	public void setUserAndPswd(String username, String password) throws FileNotFoundException, IOException{
		if(checkUser(username, password)){
			currentUser.setUsername(username);
			currentUser.setPassword(password);
		}else
			throw new IllegalArgumentException();
	}
	
	@Override
	public boolean checkUser(String username, String password) throws FileNotFoundException, IOException{
		return FileHandlerImpl.userExists(username, password);
	}
	
	private class LoginListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			try {
				setUserAndPswd(loginView.getLoginName(), loginView.getLoginPswd());
				System.out.println("Logged as: "+currentUser.getUsername()+" using password "+currentUser.getPassword());
				loginView.close();
//				new AudioPlayerImpl().initialize();
			} catch (IllegalArgumentException e1) {
				loginView.showErrorMessage("Login fallito", "Username o password errati");
			} catch(Exception ex){
				loginView.showErrorMessage("Login fallito", "Qualcosa è andato storto...");
			}
		}
	}
}
