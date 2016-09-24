package controller.audioplayer;

import java.awt.Point;
import java.awt.event.ActionEvent;
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

import controller.adder.*;
import controller.data.DataController;
import controller.player.PlayerController;
import model.PlaylistManager;
import model.Track;
import model.TrackManager;
import model.user.User;
import view.AudioPlayerGUI;
import view.AudioPlayerImpl;
public class APControllerImpl {

	private AudioPlayerGUI mainView;
	private DataController dataController;
	private AddController addCtrl;
	private PlayerController playerCtrl;
	private TrackManager trackManager;
	private PlaylistManager plManager;
	
	public APControllerImpl(User logged){
		this.mainView = new AudioPlayerImpl();
		this.trackManager = new TrackManager(logged.getUsername());
		this.plManager = new PlaylistManager(logged.getUsername());
		this.dataController = new DataController(mainView.getDataPane(), new DoubleClickAdapter());
		this.addCtrl = new AddControllerImpl(trackManager, plManager, mainView.getTrackAdder(), mainView.getPLAdder());
		this.playerCtrl = new PlayerController(mainView.getPlayer());
	}
	
	public void initializeView(){
		ActionListener[] listeners = new ActionListener[]{new ShowTracksListener(), new ShowPLListener(),
				new TrackAdderListener(), new PLAdderListener(), new DeleteListener()};
		this.mainView.addListeners(listeners);
		showTracks();
		this.mainView.initialize();
	}
	
	private void showTracks(){
		try {
			dataController.showTracksTable(getTracksToShow());
			mainView.setDataTitle("Tracce");
		} catch (ClassNotFoundException | IOException e) {
			dataController.showTracksTable(new LinkedHashMap<>());
			e.printStackTrace();
		}
	}
	
	private void showPlaylists(){
		try{
			dataController.showPLTable(getPlaylistsToShow());
			mainView.setDataTitle("Playlist");
		}catch(ClassNotFoundException | IOException e){
			dataController.showPLTable(new ArrayList<>());
		}
		
	}
	
	private Map<String, Float> getTracksToShow() throws FileNotFoundException, ClassNotFoundException, IOException{
		Map<String, Float> retMap = new LinkedHashMap<>();
		trackManager.retrieveOrdered().forEach(e-> retMap.put(e.getName(), e.getDuration()));
		return retMap;
	}
	
	private List<String> getPlaylistsToShow() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<String> retList = new ArrayList<>();
		plManager.retrieveOrdered().forEach(e->retList.add(e.getName()));
		return new ArrayList<>(retList);
	}
	
	private class ShowTracksListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
				showTracks();
		}
	}
	
	private class ShowPLListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showPlaylists();
		}
	}
	
	private class TrackAdderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				addCtrl.showTrackAdder();
				showTracks();
		}
	}
	
	private class PLAdderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				addCtrl.showPLAdder();
		}
	}
	
	private class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String toDelete = dataController.getSelected();
			String currentlyShown = dataController.getCurrentView();
			if(currentlyShown.equals(DataController.TRACKSVIEW)){
				trackManager.deleteTrack(toDelete);
				try {
					plManager.removeTrack(toDelete);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showTracks();
			}
			else if(currentlyShown.equals(DataController.PLAYLISTSVIEW)){
				plManager.deletePlaylist(toDelete);
				showPlaylists();
			}
		}
	}
	
	private class DoubleClickAdapter extends MouseAdapter{
		
		@Override
		public void mousePressed(MouseEvent me){
			JTable table =(JTable) me.getSource();
		    Point p = me.getPoint();
		    int row = table.rowAtPoint(p);
		    if (me.getClickCount() == 2) {
		    	String current = dataController.getCurrentView();
		        String selected = (String) table.getValueAt(row, 0);
		        if(current.equals(DataController.PLAYLISTSVIEW)){
		           	try {
						playerCtrl.setPlayQueue(plManager.retrieve(selected).getTracks());
					} catch (ClassNotFoundException | IOException e) {
						dataController.nothingFound("Impossibile riprodurre la playlist");
					}
		        }else{
		        	
		        	try {
		        		List<Track> toPlay = new ArrayList<>();
						toPlay.add(trackManager.retrieve(selected));
						playerCtrl.setPlayQueue(toPlay);
					} catch (ClassNotFoundException | IOException e) {
						dataController.nothingFound("Impossibile riprodurre il brano");
					}
		        }
		        playerCtrl.startQueue();  
		    }
		}
	}
}
