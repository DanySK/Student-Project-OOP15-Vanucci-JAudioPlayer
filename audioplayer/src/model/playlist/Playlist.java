package model.playlist;

import java.util.List;

import model.track.Track;

public interface Playlist {

	/**
	 * 
	 * @param track
	 * adds a track to this playlist
	 */
	void addTrack(Track track);
	
	/**
	 * 
	 * @param trackName
	 * @return a boolean flag
	 * removes a track from this playlist
	 */
	boolean removeTrack(String trackName);
	
	/**
	 * 
	 * @return a list with all the track in this playlist
	 *
	 */
	List<Track> getTracks();
	
	
	/**
	 * 
	 * @return the playlist name
	 */
	String getName();
	
	
	/**
	 * 
	 * @param index
	 * @return a track from the playlist
	 */
	Track getTrack(int index);
	
	
}
