package controller;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import model.PlaylistManager;
import model.TrackManager;
import view.tables.DataPane;

public class DataController {
	
	public static final String TRACKSVIEW = "Tracks";
	public static final String PLAYLISTSVIEW = "Playlists";
	
	private String currentlyShown;
	private TrackManager trackManager;
	private PlaylistManager plManager;
	private DataPane dataPane;

	public DataController(TrackManager trackManager, PlaylistManager plManager, DataPane dataPane, MouseAdapter adapter){
		this.trackManager = trackManager;
		this.plManager = plManager;
		this.dataPane = dataPane;
		this.dataPane.setAdapter(adapter);
	}
	
//	public void addTrack(final String filePath, final String name) throws UnsupportedAudioFileException, IOException, ClassNotFoundException{
//		File file = new File(filePath);
//		Track toAdd = new TrackImpl(file, name);
//		trackManager.addTrack(toAdd);
//	}
//	
//	public void createPlaylist(String plName, List<String> trackNames) throws FileNotFoundException, ClassNotFoundException, IOException{
//		plManager.create(plName, trackNames);
//	}
//	
//	public void showTracks(){
//		
//	}
	
	public String getCurrentView(){
		return new String(currentlyShown);
	}
	
	public void showTracksTable(){
		try {
			dataPane.showTracks(getTracks());
			currentlyShown = TRACKSVIEW;
		} catch (ClassNotFoundException | IOException e) {
			dataPane.showTracks(new LinkedHashMap<>());
			e.printStackTrace();
		}
	}
	
	public void showPLTable(){
		try {
			dataPane.showPlaylists(getPlaylists());
			currentlyShown = PLAYLISTSVIEW;
		} catch (ClassNotFoundException | IOException e) {
			dataPane.showPlaylists(new ArrayList<>());
			e.printStackTrace();
		}
	}
	
	public void nothingFound(String message){
		dataPane.showErrorMessage("Non ho trovato nulla", message);
	}
	
	public Map<String, Float> getTracks() throws FileNotFoundException, ClassNotFoundException, IOException{
		Map<String, Float> retMap = new LinkedHashMap<>();
		trackManager.retrieveOrdered().forEach(e-> retMap.put(e.getName(), e.getDuration()));
		return retMap;
	}
	
	public List<String> getPlaylists() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<String> retList = new ArrayList<>();
		plManager.retrieveOrdered().forEach(e->retList.add(e.getName()));
		return retList;
	}
}
