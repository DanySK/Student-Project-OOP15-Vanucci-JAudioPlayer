package controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import controller.app.APControllerImpl;
import model.FileHandler;
import model.user.User;
import model.user.UserManager;
import view.login.LoginGUI;
import view.login.LoginImpl;

public class LoginControllerImpl implements LoginController{
	
	private static final String USERS_FILE = "users.txt";

	private LoginGUI loginView;
	
	public LoginControllerImpl(){
		this.loginView = new LoginImpl();
	}
	
	/**
	 * Initializes the login view
	 */
	@Override
	public void initializeView(){
		String usersPath = FileHandler.getMainDir()+USERS_FILE;
		if(!new File(usersPath).exists()){
			try {
				Files.copy(getClass().getResourceAsStream("/users/users.txt"), Paths.get(usersPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.loginView.initializeGUI();
		loginView.addActionListener(new LoginListener());
	}
	
	/**
	 * calls a check for the existence of the user and then sets it
	 */
	@Override
	public void setUserAndPswd(String username, String password) throws FileNotFoundException, IOException{
		if(checkUser(username, password)){
			UserManager.setUser(username, password);
		}else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Checks if the user exists in the userFile calling the UserManager
	 */
	@Override
	public boolean checkUser(String username, String password) throws FileNotFoundException, IOException{
		return UserManager.userExists(username, password);
	}
	
	private class LoginListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			try {
				setUserAndPswd(loginView.getLoginName(), loginView.getLoginPswd());
				User currentUser = UserManager.getUser();
				new APControllerImpl(currentUser).initializeView();
				loginView.close();
			} catch (IllegalArgumentException e1) {
				loginView.showErrorMessage("Login fallito", "Username o password errati");
			} catch(Exception ex){
				loginView.showErrorMessage("Login fallito", "Qualcosa è andato storto...");
				ex.printStackTrace();
			}
		}
	}
}
