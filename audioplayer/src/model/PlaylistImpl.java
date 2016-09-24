package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaylistImpl implements Playlist, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3620295728810350543L;
	
	private String name;
	private List<Track> tracks;
	
	public PlaylistImpl(String name, List<Track> tracks){
		this.name = name;
		this.tracks = tracks;
	}

	@Override
	public void addTrack(final Track track) {
		
		this.tracks.add(track);
	}

	@Override
	public boolean removeTrack(String trackName) {
		System.out.println("Voglio eliminare "+trackName);
		return this.tracks.removeIf(e->e.getName().equals(trackName));
	}

	@Override
	public List<Track> getTracks() {

		return new ArrayList<Track>(this.tracks);
	}

	@Override
	public String getName() {
		return new String(this.name);
	}

	@Override
	public Track getTrack(int index) {
		
		return tracks.get(index);
	}

}
