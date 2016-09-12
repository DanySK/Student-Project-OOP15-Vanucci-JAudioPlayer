package view;

import javax.swing.JFrame;
import javax.swing.BoxLayout;

import controller.user.UserHandler;
import view.player.Player;
import view.tables.TableView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ContentPane extends JFrame implements AudioPlayerGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -659289932713557676L;
	
	private JTable tracksTable;
	private JPanel optionsPanel;
	
	public ContentPane(){
		this.setTitle("AUDIO PLAYER APP");
		this.getContentPane().add(new Player(), BorderLayout.SOUTH);
		optionsPanel = new JPanel();
		optionsPanel.setBorder(new EmptyBorder(5, 2, 5, 2));
		getContentPane().add(optionsPanel, BorderLayout.WEST);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		JButton tracksBtn = new JButton("Tracce");
		tracksBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		optionsPanel.add(tracksBtn);
		
		JButton playlistBtn = new JButton("Le mie playlist");
		playlistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		optionsPanel.add(playlistBtn);
		
		JButton createBtn = new JButton("Crea playlist");
		optionsPanel.add(createBtn);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		tracksTable = new JTable();
		JScrollPane scrollPane = new TableView(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollPane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		String name = UserHandler.getUsername();
		String password = UserHandler.getPassword();
		System.out.println("Using the user named "+name+" and password "+password);
		pack();
	}

	@Override
	public void initialize() {
		this.setVisible(true);
	}
}
