package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;

import controller.DataController;
import view.create.PlaylistAdder;
import view.create.TrackAdder;
import view.player.Player;
import view.tables.DataPane;
import view.tables.DataPaneImpl;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.CardLayout;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class AudioPlayerImpl extends JFrame implements AudioPlayerGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -659289932713557676L;
	
	private JPanel optionsPanel;

	private Player player;
	private TrackAdder trackAdder;
	private PlaylistAdder plAdder;
	private DataPaneImpl scrollPane;
	
	private JButton tracksBtn;
	private JButton playlistBtn;
	private JButton trkAddBtn;
	private JButton plAddBtn;
	
	public AudioPlayerImpl(){
		this.setTitle("AUDIO PLAYER APP");
		this.player = new Player();
		this.getContentPane().add(player, BorderLayout.SOUTH);
		optionsPanel = new JPanel();
		optionsPanel.setBorder(new EmptyBorder(5, 2, 5, 2));
		getContentPane().add(optionsPanel, BorderLayout.WEST);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		trackAdder = new TrackAdder();
		plAdder = new PlaylistAdder();
		
		this.scrollPane = new DataPaneImpl(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		scrollPane.setAdapter(new DoubleClickAdapter());
		tracksBtn = new JButton("Le mie Tracce");
		optionsPanel.add(tracksBtn);
		
		playlistBtn = new JButton("Le mie playlist");
		optionsPanel.add(playlistBtn);
		
		trkAddBtn = new JButton("Aggiungi brano");
		optionsPanel.add(trkAddBtn);
		
		plAddBtn = new JButton("Crea playlist");
		optionsPanel.add(plAddBtn);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		panel.add(scrollPane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void initialize() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void addListeners(ActionListener[] listeners){
		tracksBtn.addActionListener(listeners[0]);
		playlistBtn.addActionListener(listeners[1]);
		trkAddBtn.addActionListener(listeners[2]);
		plAddBtn.addActionListener(listeners[3]);
	}
	
	@Override
	public TrackAdder getTrackAdder(){
		return this.trackAdder;
	}
	
	@Override
	public PlaylistAdder getPLAdder(){
		return this.plAdder;
	}
	
	@Override
	public DataPane getDataPane() {
		return this.scrollPane;
	}
	
	@Override
	public Player getPlayer(){
		return this.player;
	}

	@Override
	public void showErrorMessage(String title, String content){
		JOptionPane.showMessageDialog(this, content, title, JOptionPane.ERROR_MESSAGE);
	}
}
