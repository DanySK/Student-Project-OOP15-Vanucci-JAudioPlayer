package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import model.Playlist;
import model.PlaylistManager;
import model.Track;
import model.TrackImpl;
import model.TrackManager;

public class OptionsManager {

	private TrackManager trackManager;
	private PlaylistManager plManager;
	private List<Track> userTracks;
	private List<Playlist> userPlaylists;
	private String lastPath;
	
	public OptionsManager(String username){
		
		this.trackManager = new TrackManager(username);
		this.plManager = new PlaylistManager(username);
	}
	
	public void addTrack(String filePath, String name) throws UnsupportedAudioFileException, IOException, ClassNotFoundException{
		File file = new File(filePath);
		Track toAdd = new TrackImpl(file, name);
		trackManager.addTrack(toAdd);
	}
	
	public void createPlaylist(String plName, List<String> trackNames) throws FileNotFoundException, ClassNotFoundException, IOException{
		plManager.create(plName, trackNames);
	}
	
	private Track getTrack(String trackName) throws FileNotFoundException, ClassNotFoundException, IOException{
		return trackManager.retrieve(trackName);
	}
	
	public Map<String, Float> getTracks() throws FileNotFoundException, ClassNotFoundException, IOException{
		this.userTracks = trackManager.retrieveAll();
		Map<String, Float> retMap = new HashMap<>();
		userTracks.forEach(e-> retMap.put(e.getName(), e.getDuration()));
		return retMap;
	}
	
	public List<String> getPlaylists() throws FileNotFoundException, ClassNotFoundException, IOException{
		this.userPlaylists = plManager.retrieveAll();
		List<String> retList = new ArrayList<>();
		userPlaylists.forEach(e->retList.add(e.getName()));
		return retList;
	}
	
	public String getTrackPath(String trackName){
		String retString = null;
		for(Track track: userTracks){
			if(track.getName().equals(trackName))
				retString = track.getFilePath();
		}
		return retString;
	}
	
	public String chooser() {
		JFileChooser fileChooser = null;
		
		if (lastPath != null && !lastPath.equals("")) {
			fileChooser = new JFileChooser(lastPath);
		} else {
			fileChooser = new JFileChooser();
		}
		
		FileFilter wavFilter = new FileFilter() {
			@Override
			public String getDescription() {
				return "Sound file (*.WAV)";
			}

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					return file.getName().toLowerCase().endsWith(".wav");
				}
			}
		};

		
		fileChooser.setFileFilter(wavFilter);
		fileChooser.setDialogTitle("Seleziona il file da aggiungere");
		fileChooser.setAcceptAllFileFilterUsed(false);

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			lastPath = fileChooser.getSelectedFile().getParent();
			return fileChooser.getSelectedFile().getAbsolutePath();
		}
		
		return null;
	}
}
