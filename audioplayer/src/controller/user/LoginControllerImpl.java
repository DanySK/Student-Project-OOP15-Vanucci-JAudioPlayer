package controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.app.APControllerImpl;
import model.user.User;
import model.user.UserManagerImpl;
import view.login.LoginGUI;
import view.login.LoginImpl;

public class LoginControllerImpl implements LoginController{

	private LoginGUI loginView;
	
	public LoginControllerImpl(){
		this.loginView = new LoginImpl();
	}
	
	@Override
	public void initializeView(){
		this.loginView.initializeGUI();
		loginView.addActionListener(new LoginListener());
	}
	
	@Override
	public void setUserAndPswd(String username, String password) throws FileNotFoundException, IOException{
		if(checkUser(username, password)){
			UserManagerImpl.setUser(username, password);
		}else
			throw new IllegalArgumentException();
	}
	
	@Override
	public boolean checkUser(String username, String password) throws FileNotFoundException, IOException{
		return UserManagerImpl.userExists(username, password);
	}
	
	private class LoginListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			try {
				setUserAndPswd(loginView.getLoginName(), loginView.getLoginPswd());
				User currentUser = UserManagerImpl.getUser();
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
