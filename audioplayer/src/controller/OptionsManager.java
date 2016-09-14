package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

import model.Track;
import model.TrackImpl;
import model.TrackManager;

public class OptionsManager {

	private TrackManager trackManager;
	
	public OptionsManager(String username){
		
		this.trackManager = new TrackManager(username);
	}
	
	public void addTrack(String filePath, String name) throws UnsupportedAudioFileException, IOException, ClassNotFoundException{
		
		File file = new File(filePath);
		Track toAdd = new TrackImpl(file, name);
		trackManager.addTrack(toAdd);
	}
	
	public Map<String, Float> getTracks() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Track> tracks = trackManager.retrieveAll();
		Map<String, Float> retMap = new HashMap<>();
		tracks.forEach(e-> retMap.put(e.getName(), e.getDuration()));
		return retMap;
	}
}
