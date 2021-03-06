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
	
	private static final String USER_PREFIX = "username: ";
	private static final String PSW_PREFIX = "password: ";
	private static final String SEPARATOR = ", ";
	private static User currentUser = new UserImpl();
	
	private UserManager(){}
	
	public static void setUser(String username, String password){
		currentUser.setUsername(username);
		currentUser.setPassword(password);
	}
	
	public static User getUser(){
		return currentUser;
	}
	
	/**
	 * Checks if the user exists in the users.txt file
	 * @param username
	 * @param password
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean userExists(String username, String password) throws FileNotFoundException, IOException{
		try(BufferedReader br = new BufferedReader(new InputStreamReader(FileHandler.getFile("users.txt")))) {
			String login = USER_PREFIX+username+SEPARATOR+PSW_PREFIX+password;
			if(!username.trim().equals("") || !password.trim().equals("")){
				for(String line; (line = br.readLine()) != null; ) {
					if(line.equals(login)){
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
