package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TrackManager {

	private static final String SEPARATOR = System.getProperty("file.separator");
	private static final String TRACKS_DIR = "Tracks";
	private static final String EXTENSION = ".dat";
	private FileHandler handler;
	private String tracksPath;
	
	public TrackManager(String username){
		
		this.tracksPath = FileHandler.getDir()+SEPARATOR+username+SEPARATOR+TRACKS_DIR;
		handler = new FileHandler(this.tracksPath);
	}
	
	public Track retrieve(String trackName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream reader = new ObjectInputStream(handler.getFile(trackName+EXTENSION));
		return (Track)reader.readObject();
	}
	
	public List<Track> retrieveAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Track> userTracks = new ArrayList<>();
		File tracksDir = new File(tracksPath);
		System.out.println("File: "+tracksDir.getPath()+", Path: "+tracksPath);
		if(!tracksDir.exists()){
			System.out.println("Non esiste");
			boolean so = tracksDir.mkdir();
			System.out.println(so);
		}
		for(File trackFile : handler.getFiles()){
			userTracks.add(retrieve(trackFile.getName().replace(EXTENSION, "")));
		}
		return userTracks;
	}
	
	public void addTrack(Track newTrack) throws FileNotFoundException, IOException, ClassNotFoundException{
		if(handler.trackExists(newTrack.getName()+EXTENSION))
			throw new IllegalArgumentException();
		
		ObjectOutputStream writer = new ObjectOutputStream(handler.toFile(newTrack.getName()+EXTENSION));
		writer.writeObject(newTrack);
//		Set<Track> current = new TreeSet<>(new SerializableComparator().compare(arg0, arg1));
//		if(handler.exists()){
//			current = retrieve();	
//		}
//		current.add(newTrack);
		
//		writer.writeObject(current);
	}
}
