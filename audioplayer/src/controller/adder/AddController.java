package controller.adder;

/**
 * This is the interface for the AddController, which handles the TrackAdder and PlaylistAdder views
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;

import model.track.Track;

public interface AddController {

	/**
	 * Calls the track manager to save the new track
	 * @param trackName the name of the new track
	 * @param trackFile the file of the new track
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	void saveTrack(String trackName, String trackFile) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException;
	
	/**
	 * Calls the track manager to save the new playlist
	 * @param plName the new playlist name
	 * @param plTracks the list containing the tracks to include
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	void savePlaylist(String plName, List<Track> plTracks) throws FileNotFoundException, ClassNotFoundException, IOException;

	/**
	 * Shows the TrackAdder Dialog
	 */
	void showTrackAdder();

	/**
	 * Shows the PlaylistAdder Dialog
	 */
	void showPLAdder();

	/**
	 * Shows the required dialog
	 * @param toShow
	 */
	void showDialog(JDialog toShow);

	/**
	 * checks if the string is valid
	 * @param toCheck
	 * @return the result of the control
	 */
	boolean checkString(String toCheck);

}
