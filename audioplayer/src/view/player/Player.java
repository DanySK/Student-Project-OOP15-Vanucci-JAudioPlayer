package view.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;

/**
 * This is the player view, it handles only the showing of the player's buttons and the visual infos for the
 * running track. This class, as it's controller, is based on the Swing Audio Player Sample, with a great deal
 * of modifications in order to transform part of the layout and remove every logic aspect.
 * The original Swing Audio Player Sample can be found here 
 * <a href="http://www.codejava.net/coding/java-audio-player-sample-application-in-swing">
 * http://www.codejava.net/coding/java-audio-player-sample-application-in-swing</a>
 *
 */
public class Player extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6393401803900949981L;
	
	private static final String NAMELABEL_MESSAGE = "In riproduzione: ";
	
	private int queueLength;
	
	private JLabel nameLabel = new JLabel(NAMELABEL_MESSAGE);
	private JLabel timeCounterLabel = new JLabel("00:00:00");
	private JLabel durationLabel = new JLabel("00:00:00");
	
	private JButton pauseBtn = new JButton("");
	private JButton playBtn = new JButton("");
	private JButton prevBtn = new JButton("");
	private JButton nextBtn = new JButton("");
	
	private JSlider timeSlider = new JSlider();
	
	// Icons used for buttons
	private ImageIcon iconPlay = new ImageIcon(getClass().getResource(
			"/icons/play.png"));
	private ImageIcon iconStop = new ImageIcon(getClass().getResource(
			"/icons/stop.png"));
	private ImageIcon iconPause = new ImageIcon(getClass().getResource(
			"/icons/pause.png"));
	private ImageIcon iconPrev = new ImageIcon(getClass().getResource(
			"/icons/prev.png"));
	private ImageIcon iconNext = new ImageIcon(getClass().getResource(
			"/icons/next.png"));
	
	
	public Player() {
		setLayout(new GridBagLayout());
		this.setBorder(new LineBorder(Color.DARK_GRAY));
		this.getInsets().set(5, 5, 5, 5);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.WEST;
		
		prevBtn.setIcon(iconPrev);
		prevBtn.setEnabled(false);
		prevBtn.setFocusPainted(false);
		
		nextBtn.setIcon(iconNext);
		nextBtn.setEnabled(false);
		nextBtn.setFocusPainted(false);
		
		playBtn.setIcon(iconPlay);
		playBtn.setEnabled(false);
		playBtn.setFocusPainted(false);
		
		pauseBtn.setIcon(iconPause);
		pauseBtn.setEnabled(false);
		pauseBtn.setFocusPainted(false);
		
		timeCounterLabel.setFont(new Font("Sans", Font.BOLD, 12));
		durationLabel.setFont(new Font("Sans", Font.BOLD, 12));
		
		timeSlider.setPreferredSize(new Dimension(400, 20));
		timeSlider.setEnabled(false);
		timeSlider.setValue(0);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		add(nameLabel, constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		add(timeCounterLabel, constraints);
		
		constraints.gridx = 1;
		add(timeSlider, constraints);
		
		constraints.gridx = 2;
		add(durationLabel, constraints);
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		panelButtons.add(prevBtn);
		panelButtons.add(playBtn);
		panelButtons.add(pauseBtn);
		panelButtons.add(nextBtn);
		
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(panelButtons, constraints);
	}
	
	/**
	 * Sets the dimension of the tracks queue
	 * @param value
	 */
	public void setQueueDim(int value){
		this.queueLength = value;
	}
	
	/**
	 * Sets up the button listeners
	 * @param prev
	 * @param next
	 * @param play
	 * @param pause
	 */
	public void setupButtons(ActionListener prev, ActionListener next, 
									ActionListener play, ActionListener pause){
		this.prevBtn.addActionListener(prev);
		this.nextBtn.addActionListener(next);
		this.playBtn.addActionListener(play);
		this.pauseBtn.addActionListener(pause);
	}

	/**
	 * Sets the current of the duration JSlider
	 * @param value
	 */
	public void setSliderValue(int value){
		this.timeSlider.setValue(value);
	}
	
	/**
	 * Sets the maximum value for the duration JSlider
	 * @param value
	 */
	public void setSliderMax(int value){
		this.timeSlider.setMaximum(value);
	}
	
	/**
	 * Sets the current duration on the timeCounterLabel
	 * @param currentTime
	 */
	public void setTimeLabelValue(String currentTime){
		this.timeCounterLabel.setText(currentTime);
	}
	
	/**
	 * Sets the max duration time on the durationLabel
	 * @param duration
	 */
	public void setDurationLabelValue(String duration){
		this.durationLabel.setText(duration);
	}
	
	/**
	 * Sets the name of the track to show
	 * @param name
	 */
	public void setNameLabel(String name){
		this.nameLabel.setText(NAMELABEL_MESSAGE+name);
	}
	
	/**
	 * Sets the buttons for a "Playing" status
	 * @param position
	 */
	public void resumePlaying(int position){
		this.playBtn.setIcon(iconStop);
		this.pauseBtn.setBackground(null);
		this.playBtn.setEnabled(true);
		this.pauseBtn.setEnabled(true);
		setTrackChange(position);
	}
	
	/**
	 * Sets the buttons for a "Stopped" status
	 */
	public void stopPlaying(){
		this.playBtn.setIcon(iconPlay);
		this.pauseBtn.setEnabled(false);
	}
	
	/**
	 * Sets the buttons for a "Paused" status
	 */
	public void pausePlaying(){
		this.pauseBtn.setBackground(Color.BLUE);
	}
	
	/**
	 * Shows a message dialog with the input content
	 * @param title
	 * @param message
	 */
	public void showErrorMessage(String title, String message){
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Updates the Prev and Next Buttons disabling/enabling in case it's needed
	 * @param position
	 */
	private void setTrackChange(int position){
		if(position <= 0){
			this.prevBtn.setEnabled(false);
		}else{
			this.prevBtn.setEnabled(true);
		}
		if(position >= queueLength){
			this.nextBtn.setEnabled(false);
		}else{
			this.nextBtn.setEnabled(true);
		}
	}
}
