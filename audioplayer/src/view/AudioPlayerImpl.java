package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import view.create.PlaylistAdder;
import view.create.TrackAdder;
import view.data.DataPane;
import view.data.DataPaneImpl;
import view.player.Player;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class AudioPlayerImpl extends JFrame implements AudioPlayerGUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -659289932713557676L;
	
	private JPanel optionsPanel;

	private Player player;
	private TrackAdder trackAdder;
	private PlaylistAdder plAdder;
	private TitledBorder dataPaneBorder;
	private DataPaneImpl scrollPane;
	
	private JButton tracksBtn;
	private JButton playlistBtn;
	private JButton trkAddBtn;
	private JButton plAddBtn;
	private JButton deleteBtn;
	
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
		tracksBtn = new JButton("Le mie Tracce");
		optionsPanel.add(tracksBtn);
		playlistBtn = new JButton("Le mie playlist");
		optionsPanel.add(playlistBtn);
		trkAddBtn = new JButton("Aggiungi brano");
		optionsPanel.add(trkAddBtn);
		plAddBtn = new JButton("Crea playlist");
		optionsPanel.add(plAddBtn);
		deleteBtn = new JButton("Elimina");
		optionsPanel.add(deleteBtn);
		resizeButtons();
		JPanel panel = new JPanel(new BorderLayout());
		dataPaneBorder = BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "",
                TitledBorder.LEFT,
                TitledBorder.TOP);
		panel.setBorder(dataPaneBorder);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.add(scrollPane, BorderLayout.CENTER);
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
		deleteBtn.addActionListener(listeners[4]);
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
	public void setDataTitle(String title){
		this.dataPaneBorder.setTitle(title);
		this.repaint();
	}

	@Override
	public void showErrorMessage(String title, String content){
		JOptionPane.showMessageDialog(this, content, title, JOptionPane.ERROR_MESSAGE);
	}
	
	private void resizeButtons(){
		for(Component component: optionsPanel.getComponents()){
			if(component instanceof JButton){
				component.setMaximumSize(new Dimension(200, 30));
			}
		}
	}
}
