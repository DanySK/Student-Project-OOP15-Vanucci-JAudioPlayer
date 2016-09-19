package model;

import java.awt.Image;
import java.io.IOException;
import java.util.Optional;

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
	
//	/**
//	 * 
//	 * @return the track picture (if present)
//	 */
//	Optional<Image> getPic();
//	/**
//	 * 
//	 * @param image
//	 * Sets the input image as track picture
//	 */
//	void setPic(Image image);
	
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
	 * @return the track ID
	 */
	String getID();
	/**
	 * 
	 * @return a string containing this track infos
	 */
	String toString();
}
