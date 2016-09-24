package model.user;

/**
 * This is the interface for the User Class
 * @author Francesco
 *
 */
public interface User {

	String getUsername();
	String getPassword();
	void setUsername(String username);
	void setPassword(String password);
	
	boolean isPresent();
}
