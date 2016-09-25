package model.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import model.FileHandler;
/**
 * This manager handles the user, it uses the Singleton pattern to admit only one user
 * @author Francesco
 *
 */
public class UserManager{
	
	private static User currentUser = new UserImpl();
	
	private UserManager(){}
	
	public static void setUser(String username, String password){
		currentUser.setUsername(username);
		currentUser.setPassword(password);
	}
	
	public static User getUser(){
		return currentUser;
	}
	
	public static boolean userExists(String username, String password) throws FileNotFoundException, IOException{
		try(BufferedReader br = new BufferedReader(new InputStreamReader(FileHandler.getFile("users.txt")))) {
			if(!username.trim().equals("") || !password.trim().equals("")){
				for(String line; (line = br.readLine()) != null; ) {
					if(line.contains("username: "+username) && line.contains("password: "+password)){
						String userDir = FileHandler.getMainDir();
						if(!new File(userDir+username).exists()){
							new File(userDir+username).mkdir();
						}
						return true;
					}
				}
			}
			return false;
		}
	}
}
