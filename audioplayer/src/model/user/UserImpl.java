package model.user;

public class UserImpl implements User{

	private String username = null, password = null;
	
	@Override
	public String getUsername() {
		return new String(this.username);
	}
	
	public String getPassword(){
		return new String(this.password);
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
		
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isPresent() {
		return this.username != null && this.password != null;
	}

}
