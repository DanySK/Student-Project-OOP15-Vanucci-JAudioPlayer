package model;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TrackManager {

	private static final String SEPARATOR = System.getProperty("file.separator");
	private static final String TRACKS_FILE = "tracks.dat";
	private FileHandler handler;
	
	private Set<Track> tracks;
	private String userPath;
	
	public TrackManager(String username){
		
		this.userPath = FileHandler.getDir()+SEPARATOR+username+SEPARATOR+TRACKS_FILE;
		handler = new FileHandler(this.userPath);
	}
	
	public Set<Track> retrieve() throws FileNotFoundException, IOException, ClassNotFoundException{
		Set<Track> trackSet = new TreeSet<>();
		ObjectInputStream reader = new ObjectInputStream(handler.getFile());
		while(true){
			try {
				trackSet.add((Track)reader.readObject());
			} catch (EOFException e) {
				return trackSet;
			}
		}
	}
	
	public void addTrack(Track newTrack) throws FileNotFoundException, IOException, ClassNotFoundException{
		Set<Track> current = new TreeSet<>();
		if(handler.exists()){
			current = retrieve();	
		}
		current.add(newTrack);
		ObjectOutputStream writer = new ObjectOutputStream(handler.toFile());
		writer.writeObject(current);
	}
}
