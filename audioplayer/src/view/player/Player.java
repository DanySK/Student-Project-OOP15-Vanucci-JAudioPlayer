package view.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controller.player.PlayerController;

public class Player extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6393401803900949981L;
	
	private PlayerController controller = new PlayerController();
	private Thread playbackThread;
	private TrackTimer timer;

//	private boolean isPlaying = false;
//	private boolean isPause = false;
	
	private String currentTrack;
	private String audioFilePath;
	
	private JLabel labelFileName = new JLabel("In riproduzione:");
	private JLabel labelTimeCounter = new JLabel("00:00:00");
	private JLabel labelDuration = new JLabel("00:00:00");
	
	private JButton buttonPause = new JButton("");
	private JButton buttonPlay = new JButton("");
	private JButton buttonPrev = new JButton("");
	private JButton buttonNext = new JButton("");
	
	private JSlider sliderTime = new JSlider();
	
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
		
		buttonPrev.setIcon(iconPrev);
		buttonPrev.setEnabled(false);
		buttonPrev.setFocusPainted(false);
		
		buttonNext.setIcon(iconNext);
		buttonNext.setEnabled(false);
		buttonNext.setFocusPainted(false);
		
		buttonPlay.setIcon(iconPlay);
		buttonPlay.setEnabled(false);
		buttonPlay.setFocusPainted(false);
		
		buttonPause.setIcon(iconPause);
		buttonPause.setEnabled(false);
		buttonPause.setFocusPainted(false);
		
		labelTimeCounter.setFont(new Font("Sans", Font.BOLD, 12));
		labelDuration.setFont(new Font("Sans", Font.BOLD, 12));
		
		sliderTime.setPreferredSize(new Dimension(400, 20));
		sliderTime.setEnabled(false);
		sliderTime.setValue(0);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		add(labelFileName, constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		add(labelTimeCounter, constraints);
		
		constraints.gridx = 1;
		add(sliderTime, constraints);
		
		constraints.gridx = 2;
		add(labelDuration, constraints);
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		panelButtons.add(buttonPrev);
		panelButtons.add(buttonPlay);
		panelButtons.add(buttonPause);
		panelButtons.add(buttonNext);
		
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(panelButtons, constraints);
		
		buttonPlay.addActionListener(this);
		buttonPause.addActionListener(this);
	}

	/**
	 * Handle click events on the buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source instanceof JButton) {
			JButton button = (JButton) source;
			if (button == buttonPlay) {
				if (controller.isStopped()) {
					playBack();
				} else {
					stopPlaying();
				}
			} else if (button == buttonPause) {
				if (!controller.isPaused()) {
					pausePlaying();
				} else {
					resumePlaying();
				}
			}
		}
	}

	public void openFile(String currentTrack, String filePath) {
		this.currentTrack = currentTrack;
		this.audioFilePath = filePath;
		if (!controller.isStopped()) {
			stopPlaying();
			while (controller.getAudioClip().isRunning()) {
				try {
					controller.getAudioClip().close();
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		playBack();
	}

	/**
	 * Start playing back the sound.
	 */
	private void playBack() {
		timer = new TrackTimer(labelTimeCounter, sliderTime);
		timer.start();
		playbackThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					if(controller.isStopped()){
						buttonPlay.setIcon(iconStop);
						buttonPlay.setEnabled(true);
						
						buttonPause.setEnabled(true);
					}else{
						resetControls();
					}
					
					if(controller.isPaused()){
						controller.resume();
					}
					controller.load(audioFilePath);
					timer.setAudioClip(controller.getAudioClip());
					labelFileName.setText("In riproduzione: " + currentTrack);
					sliderTime.setMaximum((int) controller.getClipSecondLength());
					
					labelDuration.setText(controller.getClipLengthString());
					controller.play();
				} catch (UnsupportedAudioFileException ex) {
					JOptionPane.showMessageDialog(Player.this,  
							"The audio format is unsupported!", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (LineUnavailableException ex) {
					JOptionPane.showMessageDialog(Player.this,  
							"Could not play the audio file because line is unavailable!", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(Player.this,  
							"I/O error while playing the audio file!", "Error", JOptionPane.ERROR_MESSAGE);
				} finally{
					
				}
			}
		});

		playbackThread.start();
	}

	private void stopPlaying() {
//		isPause = false;
		buttonPause.setEnabled(false);
		buttonPause.setBackground(null);
		timer.reset();
		timer.interrupt();
		controller.stop();
		playbackThread.interrupt();
		resetControls();
	}
	
	private void pausePlaying() {
//		isPause = true;
		buttonPause.setBackground(Color.BLUE);
		controller.pause();
		timer.pauseTimer();
		playbackThread.interrupt();
	}
	
	private void resumePlaying() {
//		isPause = false;
		buttonPause.setBackground(null);
		controller.resume();
		timer.resumeTimer();
		playbackThread.interrupt();		
	}
	
	private void resetControls() {
		timer.reset();
		timer.interrupt();

		buttonPlay.setIcon(iconPlay);
		
		buttonPause.setEnabled(false);
		
//		isPlaying = false;		
	}
	
	private class TrackTimer extends Thread {
		private DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");	
		private boolean isRunning = false;
		private boolean isPause = false;
		private boolean isReset = false;
		private long startTime;
		private long pauseTime;
		
		private JLabel labelRecordTime;
		private JSlider slider;
		private Clip audioClip;
		
		public void setAudioClip(Clip audioClip) {
			this.audioClip = audioClip;
		}

		public TrackTimer(JLabel labelRecordTime, JSlider slider) {
			this.labelRecordTime = labelRecordTime;
			this.slider = slider;
		}
		
		public void run() {
			isRunning = true;
			
			startTime = System.currentTimeMillis();
			
			while (isRunning) {
				try {
					Thread.sleep(100);
					if (!isPause) {
						if (audioClip != null && audioClip.isRunning()) {
							labelRecordTime.setText(toTimeString());
							int currentSecond = (int) audioClip.getMicrosecondPosition() / 1_000_000; 
							slider.setValue(currentSecond);
						}
					} else {
						pauseTime += 100;
					}
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					if (isReset) {
						slider.setValue(0);
						labelRecordTime.setText("00:00:00");
						isRunning = false;		
						break;
					}
				}
			}
		}
		
		
		/**
		 * Reset counting to "00:00:00"
		 */
		public void reset() {
			isReset = true;
			isRunning = false;
		}
		
		public void pauseTimer() {
			isPause = true;
		}
		
		public void resumeTimer() {
			isPause = false;
		}
		
		/**
		 * Generate a String for time counter in the format of "HH:mm:ss"
		 * @return the time counter
		 */
		private String toTimeString() {
			long now = System.currentTimeMillis();
			Date current = new Date(now - startTime - pauseTime);
			dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
			String timeCounter = dateFormater.format(current);
			return timeCounter;
		}
	}
}
