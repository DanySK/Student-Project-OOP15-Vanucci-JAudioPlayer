package controller;

import model.TrackManager;

public class OptionsManager {

	private TrackManager trackManager;
	
	public OptionsManager(String username){
		
		this.trackManager = new TrackManager(username);
	}
	
	public void addTrack(String filePath, String name){
		
		
	}
}
