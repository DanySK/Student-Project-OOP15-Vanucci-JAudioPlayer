package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

public class PlaylistManager implements Observer{

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
		handler.makeDir(plPath);
		for(File plFile : handler.getFiles(plPath)){
			userPlaylists.add(retrieve(plFile.getName().replace(EXTENSION, "")));
		}
		this.playlists = userPlaylists;
		System.out.println("Sono nel PlaylistManager.retrieveAll :"+new File("C:/Users/Francesco/AudioPlayer/user1/Playlists/Funziona.dat").canWrite());
		return userPlaylists;
	}
	
	public Set<Playlist> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Playlist> sorted = new TreeSet<>((e1, e2)->e1.getName().toLowerCase().compareTo(e2.getName().toLowerCase()));
		sorted.addAll(retrieveAll());
		System.out.println("Sono nel PlaylistManager.retrieveOrdered :"+new File("C:/Users/Francesco/AudioPlayer/user1/Playlists/Funziona.dat").canWrite());
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
	
	public void deletePlaylist(String toDelete){
		if(handler.deleteFile(plPath+fileSeparator+toDelete+EXTENSION)){
			if(playlists != null){
				playlists.removeIf(e->e.getName().equals(toDelete));
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
}
