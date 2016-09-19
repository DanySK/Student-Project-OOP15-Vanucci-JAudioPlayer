package controller.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.FileHandler;
import model.user.User;
import model.user.UserImpl;

public class UserHandler {

	private static User currentUser = new UserImpl();
	private UserHandler(){
		
	}
	
	public static String getUsername(){
		
		return currentUser.getUsername();
	}
	
	public static String getPassword(){
		
		return currentUser.getPassword();
	}
	public static void setUserAndPswd(String username, String password) throws FileNotFoundException, IOException{
		if(checkUser(username, password)){
			currentUser.setUsername(username);
			currentUser.setPassword(password);
		}else
			throw new IllegalArgumentException();
	}
	
	public static boolean checkUser(String username, String password) throws FileNotFoundException, IOException{
		return FileHandler.userExists(username, password);
	}
}
