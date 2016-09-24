package model.playlist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.sampled.UnsupportedAudioFileException;

import model.AbstractDataManager;
import model.FileHandler;
import model.ObjectHandler;

public class PlaylistManager extends AbstractDataManager<Playlist>{

	private static final String PLAYLISTS_DIR = "Playlists";
	
	public PlaylistManager(String username){
		super(username, PLAYLISTS_DIR);
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
	
	public void addNew(Playlist newPL) throws FileNotFoundException, IOException, ClassNotFoundException{
		if(playlistExists(newPL.getName()))
			throw new IllegalArgumentException();
		
		ObjectHandler.objectToFile(newPL, dirPath+fileSeparator+newPL.getName()+EXTENSION);
		if(stored != null){
			stored.add(newPL);
		}
	}
	
	public void delete(String toDelete){
		if(FileHandler.deleteFile(dirPath+fileSeparator+toDelete+EXTENSION)){
			if(stored != null){
				stored.removeIf(e->e.getName().equals(toDelete));
			}
		}
	}
	
	private void removeTrack(String trackName) throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Playlist> toUpdate = new ArrayList<>();
		retrieveAll().forEach(pl->{
			if(pl.removeTrack(trackName)){
				toUpdate.add(pl);
			}
		});
		toUpdate.forEach(pl->{
			delete(pl.getName());
			try {
				addNew(pl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void update(String updated, String file) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException{
		List<Playlist> toUpdate = new ArrayList<>();
		if(file == null){
			removeTrack(updated);
		}
		else{
			retrieveAll().forEach(pl->{
				pl.getTracks().forEach(tr->{
					if(tr.getName().equals(updated))
						try {
							tr.setFile(file);
							toUpdate.add(pl);
						} catch (Exception e) {
							e.printStackTrace();
						}
				});
			});
			toUpdate.forEach(pl->{
				delete(pl.getName());
				try {
					addNew(pl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
}
