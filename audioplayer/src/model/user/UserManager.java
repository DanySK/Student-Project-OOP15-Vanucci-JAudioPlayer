package model.user;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserManager {

	User setUser(String username, String password);
	boolean userExists(String username, String password) throws FileNotFoundException, IOException;
}
