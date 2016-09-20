package controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.user.User;
import model.user.UserManager;
import model.user.UserManagerImpl;
import view.login.LoginGUI;
import view.login.LoginImpl;

public class LoginControllerImpl implements LoginController{

	private LoginGUI loginView;
	private User currentUser;
	private UserManager manager;
	
	public LoginControllerImpl(){
		this.loginView = new LoginImpl();
		this.manager = new UserManagerImpl();
	}
	
	@Override
	public void initializeView(){
		this.loginView.initializeGUI();
		loginView.addActionListener(new LoginListener());
	}
	
	@Override
	public void setUserAndPswd(String username, String password) throws FileNotFoundException, IOException{
		if(checkUser(username, password)){
			this.currentUser = manager.setUser(username, password);
		}else
			throw new IllegalArgumentException();
	}
	
	@Override
	public boolean checkUser(String username, String password) throws FileNotFoundException, IOException{
		return manager.userExists(username, password);
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
				ex.printStackTrace();
			}
		}
	}
}
