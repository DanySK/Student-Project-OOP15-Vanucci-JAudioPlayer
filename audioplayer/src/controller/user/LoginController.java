package controller.user;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the interface for the LoginCotrollerImpl class
 * @author Francesco
 *
 */
public interface LoginController {

	void initializeView();

	void setUserAndPswd(String username, String password) throws FileNotFoundException, IOException;

	boolean checkUser(String username, String password) throws FileNotFoundException, IOException;

	
}
