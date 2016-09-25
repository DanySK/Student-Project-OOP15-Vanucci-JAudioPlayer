package model.track;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface Track {

	/**
	 * returns the name of this track
	 * @return the track name
	 */
	String getName();
	/**
	 * Sets the input name as track name
	 * @param name
	 * 
	 */
	void setName(String name);
	
	/**
	 * returns the file contained in this track
	 * @return a protective copy of the track file
	 */
	File getFile();
	
	/**
	 * returns the path of the file in this track
	 * @return the track's filepath
	 */
	String getFilePath();
	/**
	 * Sets the passed file for this track
	 * @param path
	 * Sets the file for the track
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * 
	 */
	void setFile(String path) throws UnsupportedAudioFileException, IOException;
	
	/**
	 * returns the duration of the sound file in this Track
	 * @return the track duration in float
	 */
	Float getDuration();
	/**
	 * 
	 * @return a string containing this track infos
	 */
	String toString();
}
