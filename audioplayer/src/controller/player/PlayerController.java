package controller.player;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import model.Playlist;
import model.PlaylistManager;
import model.Track;
import model.TrackManager;
import view.player.Player;

public class PlayerController implements LineListener {
	
	/**
	 * this flag indicates whether the playback completes or not.
	 */
	private boolean playCompleted;

	/**
	 * this flag indicates whether the playback is stopped or not.
	 */
	private boolean isStopped = true, isPaused = false;

	private Clip audioClip;
	private TrackTimer timer;
	private Thread playbackThread;
	
	private List<Track> playQueue;
	private Track currentTrack;
	private int queueCounter = 0;
	private Player view;
	
	public PlayerController(TrackManager trackManager, PlaylistManager plManager, Player view){
		this.view = view;
		this.view.setupButtons(new PrevListener(), new NextListener(), 
									new PlayListener(), new PauseListener());
	}
	
	public void setPlayQueue(List<Track> queue){
		this.queueCounter = 0;
		this.playQueue = queue;
		view.setQueueDim(queue.size()-1);
	}
	
	public void startQueue(){
		openTrack();
	}
	
	
	public void openTrack() {
		
		this.currentTrack = playQueue.get(queueCounter);
		if (!isStopped()) {
			stop();
			while (audioClip.isRunning()) {
				try {
					audioClip.close();
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		playBack();
	}
	
	private void displayError(String title, String message){
		view.showErrorMessage(title, message);
	}
	
	/**
	 * Start playing back the sound.
	 */
	private void playBack() {
		timer = new TrackTimer();
		playbackThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					if(isStopped()){
						resume();
					}else{
						view.stopPlaying();
					}
					
					if(isPaused()){
						resume();
					}
					load();
					timer.start();
					view.setNameLabel(currentTrack.getName());
					view.setSliderMax((int) getClipSecondLength());
					
					view.setDurationLabelValue(getClipLengthString());
					play();
				} catch (UnsupportedAudioFileException ex) {
					displayError("Errore", "Il formato audio non è supportato");
				} catch (LineUnavailableException | IOException ex) {
					displayError("Errore", "Qualcosa è andato storto...");
				}
			}
		});

		playbackThread.start();
	}

	/**
	 * Load audio file before playing back
	 * 
	 * @param audioFilePath
	 *            Path of the audio file.
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public void load() throws UnsupportedAudioFileException, 
														IOException, LineUnavailableException {
		
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(currentTrack.getFile());
		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		audioClip = (Clip) AudioSystem.getLine(info);
		audioClip.addLineListener(this);
		audioClip.open(audioStream);
	}
	
	public long getClipSecondLength() {
		return audioClip.getMicrosecondLength() / 1_000_000;
	}
	
	public String getClipLengthString() {
		String length = "";
		long seconds = getClipSecondLength();
		return String.format("%d:%02d:%02d", seconds / 3600,
				(seconds % 3600) / 60, (seconds % 60));
	}

	/**
	 * Play a given audio file.
	 * 
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public void play() throws IOException {
		System.out.println("isStopped: "+isStopped+", isPaused: "+isPaused);
		audioClip.start();

		playCompleted = false;
		isStopped = false;

		while (!playCompleted) {
			// wait for the playback completes
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
//				ex.printStackTrace();
				if (isStopped) {
					audioClip.stop();
					break;
				}
				if (isPaused) {
					audioClip.stop();
				} else {
					System.out.println("Resuming");
					audioClip.start();
				}
			}
		}

		audioClip.close();

	}

	/**
	 * Stop playing back.
	 */
	private void stop() {
		isStopped = true;
		playbackThread.interrupt();
		resetControls();
	}

	private void pause() {
		isPaused = true;
		timer.pauseTimer();
		playbackThread.interrupt();
		view.pausePlaying();
	}

	private void resume() {
		isPaused = false;
		timer.resumeTimer();
		playbackThread.interrupt();	
		view.resumePlaying(queueCounter);
	}

	/**
	 * Listens to the audio line events to know when the playback completes.
	 */
	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if (type == LineEvent.Type.STOP) {
			System.out.println("Completed!");
			if (isStopped || !isPaused) {
				playCompleted = true;
			}
		}
	}
	
	private boolean isPaused(){
		return isPaused;
	}
	
	private boolean isStopped(){
		return isStopped;
	}
	
	private class TrackTimer extends Thread {
		private DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");	
		private boolean isRunning = false;
		private boolean isPause = false;
		private boolean isReset = false;
		private long startTime;
		private long pauseTime;
		
		public void run() {
			isRunning = true;
			
			startTime = System.currentTimeMillis();
			
			while (isRunning) {
				try {
					Thread.sleep(100);
					if (!isPause) {
						if (audioClip != null && audioClip.isRunning()) {
							view.setTimeLabelValue(toTimeString());
							int currentSecond = (int) audioClip.getMicrosecondPosition() / 1_000_000; 
							view.setSliderValue(currentSecond);
						}
					} else {
						pauseTime += 100;
					}
				} catch (InterruptedException ex) {
					if (isReset) {
						view.setSliderValue(0);
						view.setTimeLabelValue("00:00:00");
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
	
	private void moveInQueue(int step){
		this.queueCounter+=step;
	}
	
	private void resetControls() {
		timer.reset();
		timer.interrupt();
		view.stopPlaying();
	}
	
	private class PlayListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(isStopped()){
				playBack();
			}else{
				stop();
			}
		}
	}
	
	private class PauseListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isPaused()){
				pause();
			}else{
				resume();
			}
		}
	}
	
	private class PrevListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(queueCounter > 0){
				moveInQueue(-1);
				openTrack();
			}
		}
	}
	
	private class NextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(queueCounter < playQueue.size()-1){
				moveInQueue(1);
				openTrack();
			}
		}
	}
}