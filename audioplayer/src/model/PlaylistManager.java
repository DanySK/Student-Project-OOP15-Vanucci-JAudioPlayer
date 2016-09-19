package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistManager {

	private static final String SEPARATOR = System.getProperty("file.separator");
	private static final String PLAYLISTS_DIR = "Playlists";
	private static final String EXTENSION = ".dat";
	private TrackManager trackManager;
	private FileHandler handler;
	private String plPath;
	
	public PlaylistManager(String username){
		
		this.plPath = FileHandler.getDir()+SEPARATOR+username+SEPARATOR+PLAYLISTS_DIR;
		trackManager = new TrackManager(username);
		handler = new FileHandler(this.plPath);
	}
	
	public Playlist retrieve(String plName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream reader = new ObjectInputStream(handler.getFile(plName+EXTENSION));
		return (Playlist)reader.readObject();
	}
	
	public List<Playlist> retrieveAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Playlist> userPlaylists = new ArrayList<>();
		File plDir = new File(plPath);
		System.out.println("File: "+plDir.getPath()+", Path: "+plPath);
		if(!plDir.exists()){
			System.out.println("Non esiste");
			boolean so = plDir.mkdir();
			System.out.println(so);
		}
		for(File plFile : handler.getFiles()){
			userPlaylists.add(retrieve(plFile.getName().replace(EXTENSION, "")));
		}
		return userPlaylists;
	}
	
	public void create(String name, List<String> trackNames) throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Track> tracksList = new ArrayList<>();
		for(String track: trackNames){
			tracksList.add(trackManager.retrieve(track));
		}
		Playlist newPlaylist = new PlaylistImpl(name, tracksList);
		ObjectOutputStream writer = new ObjectOutputStream(handler.toFile(newPlaylist.getName()+EXTENSION));
		writer.writeObject(newPlaylist);
	}
}
