package model;

import java.awt.Image;
import java.util.Optional;

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
	 * @return the track picture (if present)
	 */
	Optional<Image> getPic();
	/**
	 * 
	 * @param image
	 * Sets the input image as track picture
	 */
	void setPic(Image image);
	
	/**
	 * 
	 * @return the track's filepath
	 */
	String getFilePath();
	/**
	 * 
	 * @param path
	 * Sets the file for the track
	 * 
	 */
	void setFile(String path);
	
	Long getDuration();
	
	String toString();
}
