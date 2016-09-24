package model.track;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface Track {

	/**
	 * 
	 * @return the track name
	 */
	String getName();
	/**
	 * 
	 * @param name
	 * Sets the input name as track name
	 */
	void setName(String name);
	
	/**
	 * 
	 * @return a protective copy of the track file
	 */
	File getFile();
	
	/**
	 * 
	 * @return the track's filepath
	 */
	String getFilePath();
	/**
	 * 
	 * @param path
	 * Sets the file for the track
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * 
	 */
	void setFile(String path) throws UnsupportedAudioFileException, IOException;
	
	/**
	 * 
	 * @return the track duration in float
	 */
	Float getDuration();
	/**
	 * 
	 * @return a string containing this track infos
	 */
	String toString();
}
