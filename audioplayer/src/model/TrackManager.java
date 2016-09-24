package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TrackManager extends AbstractDataManager<Track>{

	private static final String TRACKS_DIR = "Tracks";
	
	public TrackManager(String username){
		super(username, TRACKS_DIR);
	}
	
	public Set<Track> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Track> sorted = new TreeSet<>((e1, e2)->e1.getName().toLowerCase().compareTo(e2.getName().toLowerCase()));
		sorted.addAll(retrieveAll());
		return sorted;
	}
	
	private boolean trackExists(String trackName){
		for(Track track: this.stored){
			if(track.getName().equals(trackName))
				return true;
		}
		return false;
	}
	
	public void addNew(Track newTrack) throws FileNotFoundException, IOException, ClassNotFoundException{
		if(trackExists(newTrack.getName()))
			throw new IllegalArgumentException();
		
		ObjectHandler.objectToFile(newTrack, dirPath+fileSeparator+newTrack.getName()+EXTENSION);
		if(stored != null){
			stored.add(newTrack);
		}
	}
	
	public void delete(String toDelete){
		if(FileHandler.deleteFile(dirPath+fileSeparator+toDelete+EXTENSION)){
			if(stored != null){
				stored.removeIf(e->e.getName().equals(toDelete));
			}
		}
	}
	
	public void update(final String name, final String file) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException{
		Track updTrack = retrieve(name);
		delete(name);
		updTrack.setFile(file);
		addNew(updTrack);
	}
}
