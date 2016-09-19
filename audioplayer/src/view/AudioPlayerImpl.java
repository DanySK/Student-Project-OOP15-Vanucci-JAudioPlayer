package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;

import controller.OptionsManager;
import controller.user.UserHandler;
import view.create.PlaylistAdder;
import view.create.TrackAdder;
import view.player.Player;
import view.tables.DataPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class AudioPlayerImpl extends JFrame implements AudioPlayerGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -659289932713557676L;
	
	private JPanel optionsPanel;
	
	private OptionsManager manager;
	private Player player;
	private TrackAdder addWindow;
	private PlaylistAdder createWindow;
	
	public AudioPlayerImpl(){
		this.setTitle("AUDIO PLAYER APP");
		this.player = new Player();
		this.getContentPane().add(player, BorderLayout.SOUTH);
		optionsPanel = new JPanel();
		optionsPanel.setBorder(new EmptyBorder(5, 2, 5, 2));
		getContentPane().add(optionsPanel, BorderLayout.WEST);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		manager = new OptionsManager(UserHandler.getUsername());
		addWindow = new TrackAdder(manager);
		createWindow= new PlaylistAdder(manager);
		
		DataPane scrollPane = new DataPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, manager);
		scrollPane.setAdapter(new DoubleClickAdapter());
		JButton tracksBtn = new JButton("Le mie Tracce");
		tracksBtn.addActionListener(e->{
			try {
				scrollPane.showTracks();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		optionsPanel.add(tracksBtn);
		
		JButton playlistBtn = new JButton("Le mie playlist");
		playlistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					scrollPane.showPlaylists();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		optionsPanel.add(playlistBtn);
		
		JButton addBtn = new JButton("Aggiungi brano");
		addBtn.addActionListener(e-> {
			
			addWindow.setVisible(true);
			try {
				scrollPane.showTracks();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		optionsPanel.add(addBtn);
		
		JButton createBtn = new JButton("Crea playlist");
		createBtn.addActionListener(e->{
			createWindow.setVisible(true);
			try {
				scrollPane.showPlaylists();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		optionsPanel.add(createBtn);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		try {
			scrollPane.showTracks();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel.add(scrollPane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void initialize() {
		this.setVisible(true);
	}
	
	private class DoubleClickAdapter extends MouseAdapter{
		
		@Override
		public void mousePressed(MouseEvent me){
			JTable table =(JTable) me.getSource();
	        Point p = me.getPoint();
	        int row = table.rowAtPoint(p);
	        if (me.getClickCount() == 2) {
	            String selected = (String) table.getValueAt(row, 0);
	            player.openFile(selected, manager.getTrackPath(selected));
	        }
		}
	}
}
