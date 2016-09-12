package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PlaylistImpl implements Playlist{
	
	private String name;
	private Set<Track> tracks = new TreeSet<>((t1, t2)-> t1.getName().compareTo(t2.getName()));

	@Override
	public void addTrack(final Track track) {
		
		this.tracks.add(track);
	}

	@Override
	public boolean removeTrack(String trackName) {
		return this.tracks.remove(trackName);
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
		// TODO Auto-generated method stub
		return null;
	}

}
