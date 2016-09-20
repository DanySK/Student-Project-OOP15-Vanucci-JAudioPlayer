package view.login;

import java.awt.event.ActionListener;

public interface LoginGUI {

	void initializeGUI();
	
	void addActionListener(ActionListener buttonListener);
	
	String getLoginName();
	
	String getLoginPswd();

	void close();

	void showErrorMessage(String title, String content);
}
