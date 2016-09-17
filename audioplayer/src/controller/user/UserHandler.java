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
	
	private static final String MAIN_DIR = FileHandler.getDir();
	private static final String SEPARATOR = System.getProperty("file.separator");

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
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(MAIN_DIR+SEPARATOR+"users.txt")))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	String newLine = line.trim();
		        if(newLine.startsWith("username: "+username) && newLine.endsWith("password: "+password)){
		        	if(!new File(MAIN_DIR+SEPARATOR+username).exists()){
		        		new File(MAIN_DIR+SEPARATOR+username).mkdir();
		        	}
		        	return true;
		        }
		    }
		    return false;
		}
	}
}
