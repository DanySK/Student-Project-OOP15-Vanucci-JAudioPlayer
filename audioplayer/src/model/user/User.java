package model.user;

public interface User {

	String getUsername();
	String getPassword();
	void setUsername(String username);
	void setPassword(String password);
	
	boolean isPresent();
}
