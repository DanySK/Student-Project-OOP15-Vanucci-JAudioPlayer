package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TrackManager {

	private static final String TRACKS_DIR = "Tracks";
	private static final String EXTENSION = ".dat";
	
	private List<Track> tracksList = null;
	private String fileSeparator;
	private ObjectHandler handler;
	private String tracksPath;
	
	public TrackManager(String username){
		handler = new ObjectHandler();
		this.fileSeparator = handler.getSysSeparator();
		this.tracksPath = username+fileSeparator+TRACKS_DIR;
	}
	
	public Track retrieve(String trackName) throws FileNotFoundException, IOException, ClassNotFoundException{
		return (Track)handler.fileToObject(tracksPath+fileSeparator+trackName+EXTENSION);
	}
	
	public List<Track> retrieveAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		if(tracksList != null){
			return new ArrayList<>(tracksList);
		}
		List<Track> userTracks = new ArrayList<>();
		File tracksDir = new File(tracksPath);
		System.out.println("File: "+tracksDir.getPath()+", Path: "+tracksPath);
		if(!tracksDir.exists()){
			System.out.println("Non esiste");
			boolean so = tracksDir.mkdir();
			System.out.println(so);
		}
		for(File trackFile : handler.getFiles(tracksPath)){
			userTracks.add(retrieve(trackFile.getName().replace(EXTENSION, "")));
		}
		this.tracksList = userTracks;
		return userTracks;
	}
	
	public Set<Track> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Track> sorted = new TreeSet<>((e1, e2)->e1.getName().compareTo(e2.getName()));
		sorted.addAll(retrieveAll());
		return sorted;
	}
	
	private boolean trackExists(String trackName){
		for(Track track: this.tracksList){
			if(track.getName().equals(trackName))
				return true;
		}
		return false;
	}
	
	public void addTrack(Track newTrack) throws FileNotFoundException, IOException, ClassNotFoundException{
		if(trackExists(newTrack.getName()))
			throw new IllegalArgumentException();
		
		handler.objectToFile(newTrack, tracksPath+fileSeparator+newTrack.getName()+EXTENSION);
		if(tracksList != null){
			tracksList.add(newTrack);
		}
	}
	
//	public void removeTrack(String trackName){
//		handler.deleteFile(trackName+EXTENSION);
//	}
}
