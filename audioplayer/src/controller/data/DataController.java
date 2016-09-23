package controller.data;

import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.data.DataPane;

public class DataController {
	
	public static final String TRACKSVIEW = "Tracks";
	public static final String PLAYLISTSVIEW = "Playlists";
	
	private String currentlyShown;
	private DataPane dataPane;

	public DataController(DataPane dataPane, MouseAdapter adapter){
		this.dataPane = dataPane;
		this.dataPane.setAdapter(adapter);
	}
	
	public String getCurrentView(){
		return new String(currentlyShown);
	}
	
	public void showTracksTable(Map<String, Float> tracksInfos){
			dataPane.showTracks(tracksInfos);
			currentlyShown = TRACKSVIEW;
	}
	
	public void showPLTable(List<String> playlistsInfos){
			System.out.println("Sono nel DataController.showPLTable:"+new File("C:/Users/Francesco/AudioPlayer/user1/Playlists/Funziona.dat").canWrite());
			dataPane.showPlaylists(new ArrayList<>(playlistsInfos));
			currentlyShown = PLAYLISTSVIEW;
	}
	
	public String getSelected(){
		return dataPane.getSelectedString();
	}
	
	public void nothingFound(String message){
		dataPane.showErrorMessage("Non ho trovato nulla", message);
	}
}
