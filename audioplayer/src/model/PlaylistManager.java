package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.sampled.UnsupportedAudioFileException;

import model.observer.EObserver;
import model.observer.ESource;

public class PlaylistManager{

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
		if(this.playlists != null){
			return this.playlists;
		}
		List<Playlist> userPlaylists = new ArrayList<>();
		handler.makeDir(plPath);
		for(File plFile : handler.getFiles(plPath)){
			userPlaylists.add(retrieve(plFile.getName().replace(EXTENSION, "")));
		}
		this.playlists = userPlaylists;
		return userPlaylists;
	}
	
	public Set<Playlist> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Playlist> sorted = new TreeSet<>((e1, e2)->e1.getName().toLowerCase().compareTo(e2.getName().toLowerCase()));
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
	
	public void deletePlaylist(String toDelete){
		if(handler.deleteFile(plPath+fileSeparator+toDelete+EXTENSION)){
			if(playlists != null){
				playlists.removeIf(e->e.getName().equals(toDelete));
			}
		}
	}
	
	public void removeTrack(String trackName) throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Playlist> toUpdate = new ArrayList<>();
		retrieveAll().forEach(pl->{
			if(pl.removeTrack(trackName)){
				toUpdate.add(pl);
			}
		});
		toUpdate.forEach(pl->{
			deletePlaylist(pl.getName());
			try {
				addPlaylist(pl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void updatePlaylists(String updated, String file) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException{
		List<Playlist> toUpdate = new ArrayList<>();
		retrieveAll().forEach(pl->{
			pl.getTracks().forEach(tr->{
				if(tr.getName().equals(updated))
					try {
						tr.setFile(file);
						toUpdate.add(pl);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			});
		});
		toUpdate.forEach(pl->{
			deletePlaylist(pl.getName());
			try {
				addPlaylist(pl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
