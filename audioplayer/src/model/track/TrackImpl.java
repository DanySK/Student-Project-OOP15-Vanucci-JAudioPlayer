package model.track;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TrackImpl implements Track, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7762866718118204014L;
	
	private File trackFile;
	private String trackName;
	private Float duration;
	
	public TrackImpl(File trackFile, String trackName) throws UnsupportedAudioFileException, IOException{
		this.trackFile = trackFile;
		this.trackName = trackName;
		setDuration();
	}
	
	@Override
	public String getName() {
		return new String(this.trackName);
	}

	@Override
	public void setName(String name) {
		this.trackName = name;
	}
	
	@Override
	public File getFile(){
		return new File(this.trackFile.getAbsolutePath());
	}

	@Override
	public String getFilePath() {
		return this.trackFile.getAbsolutePath();
	}

	@Override
	public void setFile(String path) throws UnsupportedAudioFileException, IOException {
		this.trackFile = new File(path);
		setDuration();
	}

	@Override
	public Float getDuration() {
		return new Float(this.duration);
	}
	
	private void setDuration() throws UnsupportedAudioFileException, IOException{
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.trackFile);
		AudioFormat format = audioInputStream.getFormat();
	    long audioFileLength = this.trackFile.length();
	    int frameSize = format.getFrameSize();
	    float frameRate = format.getFrameRate();
	    this.duration = (audioFileLength / (frameSize * frameRate));
	}
	
	@Override
	public String toString(){
		
		return "This track is '"+getName()+"' containing the file located at "+getFilePath();
	}

}
