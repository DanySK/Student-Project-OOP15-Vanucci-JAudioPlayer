package controller.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import model.track.Track;
import view.player.Player;

/**
 * This is the controller for the PlayerView, it handles the loading and playback for the track,
 * together with the timer thread which handles the slider and the display
 * This class in based on the Swing AudioPlayer sample found here:
 * <a href="http://www.codejava.net/coding/java-audio-player-sample-application-in-swing">
 *				http://www.codejava.net/coding/java-audio-player-sample-application-in-swing</a>
 */
public class PlayerControllerImpl implements PlayerController, LineListener {
	
	private boolean playCompleted;

	private boolean isStopped = true, isPaused = false;

	private Clip audioClip;
	private TrackTimer timer;
	private Thread playbackThread;
	
	private List<Track> playQueue;
	private Track currentTrack;
	private int queueCounter = 0;
	private Player view;
	
	public PlayerControllerImpl(Player view){
		this.view = view;
		this.view.setupButtons(new PrevListener(), new NextListener(), 
									new PlayListener(), new PauseListener());
	}
	
	/**
	 * Sets the tracks to be played in a queue and resets the counter
	 */
	@Override
	public void setPlayQueue(List<Track> queue){
		this.queueCounter = 0;
		this.playQueue = queue;
		view.setQueueDim(queue.size()-1);
	}
	
	/**
	 * This method is called by the main controller and starts playing back the queue
	 */
	@Override
	public void startQueue(){
		openTrack();
	}
	
	/**
	 * Opens the track corresponding to the current index in the queue
	 */
	private void openTrack() {
		
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
	
	/**
	 * Tells the player to display a error message dialog if occurred
	 * @param title the message title
	 * @param message the content of the message
	 */
	private void displayError(String title, String message){
		view.showErrorMessage(title, message);
	}
	
	/**
	 * Plays back the sound and sets a timer
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
	 * Loads the audiofile required
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void load() throws UnsupportedAudioFileException, 
														IOException, LineUnavailableException {
		
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(currentTrack.getFile());
		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		audioClip = (Clip) AudioSystem.getLine(info);
		audioClip.addLineListener(this);
		audioClip.open(audioStream);
	}
	
	/**
	 * Gets the duration of the current audioClip in seconds
	 * @return a long value for the clip duration
	 */
	private long getClipSecondLength() {
		return audioClip.getMicrosecondLength() / 1_000_000;
	}
	
	/**
	 * Creates a string to represent the track duration in a HH:mm:ss format
	 * @return a formatted string of the track duration
	 */
	private String getClipLengthString() {
		long seconds = getClipSecondLength();
		return String.format("%d:%02d:%02d", seconds / 3600,
				(seconds % 3600) / 60, (seconds % 60));
	}
	
	/**
	 * Handles the starting and closing of the current audioClip
	 * @throws IOException
	 */
	public void play() throws IOException {
		audioClip.start();

		playCompleted = false;
		isStopped = false;

		while (!playCompleted) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				if (isStopped) {
					audioClip.stop();
					break;
				}
				if (isPaused) {
					audioClip.stop();
				} else {
					audioClip.start();
				}
			}
		}

		audioClip.close();

	}
	
	/**
	 * Stops the track and reset the controls in the view
	 */
	private void stop() {
		isStopped = true;
		playbackThread.interrupt();
		resetControls();
	}

	/**
	 * Pauses the track, calling the view to visually show the status
	 */
	private void pause() {
		isPaused = true;
		timer.pauseTimer();
		playbackThread.interrupt();
		view.pausePlaying();
	}

	/**
	 * Resumes the playback calling the view to set the buttons
	 */
	private void resume() {
		isPaused = false;
		timer.resumeTimer();
		playbackThread.interrupt();	
		view.resumePlaying(queueCounter);
	}

	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if (type == LineEvent.Type.STOP) {
			if (isStopped || !isPaused) {
				playCompleted = true;
			}
		}
	}
	
	/**
	 * Checks if the player is currently paused
	 * @return
	 */
	private boolean isPaused(){
		return isPaused;
	}
	
	
	/**
	 * Checks if the player is currently stopped
	 * @return
	 */
	private boolean isStopped(){
		return isStopped;
	}
	
	/**
	 * This thread handles the visual timer and the slider for the player view
	 * This code can be found in the link in the PlayerControllerImpl class description
	 */
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
		 * Resets the timer
		 */
		private void reset() {
			isReset = true;
			isRunning = false;
		}
		
		/**
		 * Pauses the timer
		 */
		private void pauseTimer() {
			isPause = true;
		}
		
		/**
		 * Resumes the timer
		 */
		private void resumeTimer() {
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
	
	/**
	 * This listener handles the play button, checking the current status to act
	 * @author Francesco
	 *
	 */
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
	
	/**
	 * This listener handles the pause button
	 * @author Francesco
	 *
	 */
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
	
	/**
	 * This listener handles the Prev Button and decreases the queue counter
	 * @author Francesco
	 *
	 */
	private class PrevListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(queueCounter > 0){
				moveInQueue(-1);
				openTrack();
			}
		}
	}
	
	/**
	 * This listener handles the Next Button increasing the queue counter
	 * @author Francesco
	 *
	 */
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