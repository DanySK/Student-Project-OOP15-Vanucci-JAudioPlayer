package controller.data;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import view.data.DataPane;

public class DataControllerImpl implements DataController{
	
	public static final String TRACKSVIEW = "Tracks";
	public static final String PLAYLISTSVIEW = "Playlists";
	
	private String currentlyShown;
	private DataPane dataPane;

	public DataControllerImpl(DataPane dataPane, MouseAdapter adapter){
		this.dataPane = dataPane;
		this.dataPane.setAdapter(adapter);
	}
	
	@Override
	public String getCurrentView(){
		return new String(currentlyShown);
	}
	
	@Override
	public void showTracksTable(Map<String, Float> tracksInfos){
			dataPane.showTracks(tracksInfos);
			currentlyShown = TRACKSVIEW;
	}
	
	@Override
	public void showPLTable(List<String> playlistsInfos){
			dataPane.showPlaylists(new ArrayList<>(playlistsInfos));
			currentlyShown = PLAYLISTSVIEW;
	}
	
	@Override
	public String getSelected(){
		return dataPane.getSelectedString();
	}
	
	@Override
	public void nothingFound(String message){
		dataPane.showErrorMessage("Non ho trovato nulla", message);
	}
}
