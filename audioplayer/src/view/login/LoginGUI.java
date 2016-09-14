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

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import controller.user.UserHandler;
import view.AudioPlayerImpl;
import java.awt.Color;

public class LoginGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303346759639884086L;
	
	private final JPanel body = new JPanel();
	private final JPanel footer = new JPanel();
	private final JLabel nameLabel = new JLabel("Username: ");
	private final JLabel pswdLabel = new JLabel("Password: ");
	private final JButton login = new JButton("ACCEDI");
	private final JButton newUser = new JButton("Nuovo?");
	
	private JTextField nameIn = new JTextField();
	private JTextField pswdIn = new JTextField();
	
	public LoginGUI(){
		
		super("AudioPlayer Login");
		this.setSize(400, 230);
		nameIn.setColumns(10);
		body.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		getContentPane().add(body, BorderLayout.CENTER);
		GridBagLayout bodyLayout = new GridBagLayout();
		bodyLayout.columnWidths = new int[]{0, 0};
		bodyLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		bodyLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		bodyLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		body.setLayout(bodyLayout);
		
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 1;
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		body.add(nameLabel, gbc_nameLabel);
		
		GridBagConstraints gbc_nameIn = new GridBagConstraints();
		gbc_nameIn.insets = new Insets(0, 0, 5, 0);
		gbc_nameIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameIn.gridx = 0;
		gbc_nameIn.gridy = 2;
		body.add(nameIn, gbc_nameIn);
		
		GridBagConstraints gbc_pswdLabel = new GridBagConstraints();
		gbc_pswdLabel.anchor = GridBagConstraints.WEST;
		gbc_pswdLabel.insets = new Insets(0, 0, 5, 0);
		gbc_pswdLabel.gridx = 0;
		gbc_pswdLabel.gridy = 4;
		pswdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		pswdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		body.add(pswdLabel, gbc_pswdLabel);
		pswdIn.setColumns(10);
		
		GridBagConstraints gbc_pswdIn = new GridBagConstraints();
		gbc_pswdIn.insets = new Insets(0, 0, 5, 0);
		gbc_pswdIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_pswdIn.gridx = 0;
		gbc_pswdIn.gridy = 5;
		body.add(pswdIn, gbc_pswdIn);
		footer.setBorder(new EmptyBorder(10, 40, 10, 40));
		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new BorderLayout(0, 0));
		login.setFont(new Font("Tahoma", Font.BOLD, 14));
		login.addActionListener(e-> {
			try {
				UserHandler.setUserAndPswd(nameIn.getText(), pswdIn.getText());
				this.dispose();
				new AudioPlayerImpl().initialize();
			} catch (Exception e1) {

				JOptionPane.showMessageDialog(this, "Username o password errati", "Login failed", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		});
		footer.add(login, BorderLayout.WEST);
		newUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		footer.add(newUser, BorderLayout.EAST);
		this.setLocationRelativeTo(null);
		JRootPane root = SwingUtilities.getRootPane(login);
		root.setDefaultButton(login);
		pack();
	}
	
	public void initializeGUI(){
		
		this.setVisible(true);
	}
}
