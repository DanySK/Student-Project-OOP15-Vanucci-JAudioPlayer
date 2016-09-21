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
import java.util.TreeSet;

public class PlaylistManager {

	private static final String PLAYLISTS_DIR = "Playlists";
	private static final String EXTENSION = ".dat";
	
	private List<Playlist> playlists = null;
	private String fileSeparator;
	private ObjectHandler handler;
	private String plPath;
	
	public PlaylistManager(String username){
		
		handler = new ObjectHandler();
		this.fileSeparator = handler.getSysSeparator();
		this.plPath = username+fileSeparator+PLAYLISTS_DIR;
	}
	
	public Playlist retrieve(String plName) throws FileNotFoundException, IOException, ClassNotFoundException{
		return (Playlist)handler.fileToObject(plPath+fileSeparator+plName+EXTENSION);
	}
	
	public List<Playlist> retrieveAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		if(playlists != null){
			return new ArrayList<Playlist>(playlists);
		}
		List<Playlist> userPlaylists = new ArrayList<>();
		File playlistsDir = new File(plPath);
		System.out.println("File: "+playlistsDir.getPath()+", Path: "+plPath);
		if(!playlistsDir.exists()){
			System.out.println("Non esiste");
			boolean so = playlistsDir.mkdir();
			System.out.println(so);
		}
		for(File plFile : handler.getFiles(plPath)){
			userPlaylists.add(retrieve(plFile.getName().replace(EXTENSION, "")));
		}
		this.playlists = userPlaylists;
		return userPlaylists;
	}
	
	public Set<Playlist> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Playlist> sorted = new TreeSet<>((e1, e2)->e1.getName().compareTo(e2.getName()));
		sorted.addAll(retrieveAll());
		return sorted;
	}
	
	private boolean playlistExists(String plName) throws FileNotFoundException, ClassNotFoundException, IOException{
		for(Playlist pl: retrieveAll()){
			if(pl.getName().equals(plName))
				return true;
		}
		return false;
	}
	
	public void addPlaylist(Playlist newPL) throws FileNotFoundException, IOException, ClassNotFoundException{
		if(playlistExists(newPL.getName()))
			throw new IllegalArgumentException();
		
		handler.objectToFile(newPL, plPath+fileSeparator+newPL.getName()+EXTENSION);
		if(playlists != null){
			playlists.add(newPL);
		}
	}
	
//	public void create(String name, List<String> trackNames) throws FileNotFoundException, ClassNotFoundException, IOException{
//		List<Track> tracksList = new ArrayList<>();
//		for(String track: trackNames){
//			tracksList.add(trackManager.retrieve(track));
//		}
//		Playlist newPlaylist = new PlaylistImpl(name, tracksList);
//		ObjectOutputStream writer = new ObjectOutputStream(handler.toFile(newPlaylist.getName()+EXTENSION));
//		writer.writeObject(newPlaylist);
//	}
}
