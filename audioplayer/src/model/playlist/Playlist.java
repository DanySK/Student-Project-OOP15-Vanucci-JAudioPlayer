package model.playlist;

import java.util.List;

import model.track.Track;

public interface Playlist {

	/**
	 * adds a track to this playlist
	 * @param track
	 * 
	 */
	void addTrack(Track track);
	
	/**
	 * removes a track from this playlist
	 * @param trackName
	 * @return a boolean flag
	 * 
	 */
	boolean removeTrack(String trackName);
	
	/**
	 * returns all the tracks in the playlist
	 * @return a list with all the track in this playlist
	 *
	 */
	List<Track> getTracks();
	
	
	/**
	 * returns the name of this playlist
	 * @return the playlist name
	 */
	String getName();
	
	
	/**
	 * return the track in the defined position
	 * @param index
	 * @return a track from the playlist
	 */
	Track getTrack(int index);
	
	
}
