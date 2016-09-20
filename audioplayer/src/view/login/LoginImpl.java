package view.login;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import controller.user.LoginControllerImpl;
import view.AudioPlayerImpl;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class LoginImpl extends JFrame implements LoginGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303346759639884086L;
	
	private final JPanel body = new JPanel();
	private final JPanel footer = new JPanel();
	private final JLabel nameLabel = new JLabel("Username: ");
	private final JLabel pswdLabel = new JLabel("Password: ");
	private final JButton login = new JButton("ACCEDI");
	
	private JTextField nameIn = new JTextField();
	private JTextField pswdIn = new JTextField();
	private final JPanel upPanel = new JPanel();
	private final JPanel downPanel = new JPanel();
	
	public LoginImpl(){
		
		super("Login");
		body.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new GridLayout(0, 1, 0, 20));
		
		body.add(upPanel);
		upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
		upPanel.add(nameLabel);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		upPanel.add(nameIn);
		nameIn.setColumns(10);
		
		body.add(downPanel);
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.Y_AXIS));
		downPanel.add(pswdLabel);
		pswdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		pswdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		downPanel.add(pswdIn);
		pswdIn.setColumns(10);
		footer.setBorder(new EmptyBorder(10, 60, 10, 60));
		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		login.setFont(new Font("Tahoma", Font.BOLD, 14));
		footer.add(login, BorderLayout.CENTER);
		JRootPane root = SwingUtilities.getRootPane(login);
		root.setDefaultButton(login);
		pack();
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void initializeGUI(){
		this.setVisible(true);
	}
	
	@Override
	public void addActionListener(ActionListener buttonListener) {
		login.addActionListener(buttonListener);
		
	}
	
	@Override
	public String getLoginName(){
		return new String(nameIn.getText());
	}
	
	@Override
	public String getLoginPswd(){
		return new String(pswdIn.getText());
	}
	
	@Override
	public void close(){
		this.dispose();
	}
	
	@Override
	public void showErrorMessage(String title, String content){
		JOptionPane.showMessageDialog(this, content, title, JOptionPane.ERROR_MESSAGE);
	}
}
