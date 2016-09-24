package controller.app;

/**
 * This is the main controller for the app and handles all the interactions between the components
 * of the view and the components controllers
 */
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

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JTable;

import controller.adder.*;
import controller.data.DataControllerImpl;
import controller.player.PlayerController;
import controller.player.PlayerControllerImpl;
import model.DataManager;
import model.playlist.Playlist;
import model.playlist.PlaylistManager;
import model.track.Track;
import model.track.TrackManager;
import model.user.User;
import view.AudioPlayerGUI;
import view.AudioPlayerImpl;
public class APControllerImpl implements APController{

	private AudioPlayerGUI mainView;
	private DataControllerImpl dataController;
	private AddController addCtrl;
	private PlayerController playerCtrl;
	private DataManager<Track> trackManager;
	private DataManager<Playlist> plManager;
	
	public APControllerImpl(User logged){
		this.mainView = new AudioPlayerImpl();
		this.trackManager = new TrackManager(logged.getUsername());
		this.plManager = new PlaylistManager(logged.getUsername());
		this.dataController = new DataControllerImpl(mainView.getDataPane(), new DoubleClickAdapter());
		this.addCtrl = new AddControllerImpl(trackManager, plManager, mainView.getTrackAdder(), mainView.getPLAdder());
		this.playerCtrl = new PlayerControllerImpl(mainView.getPlayer());
	}
	
	/**
	 * Initializes the main view setting it visible and preparing the options buttons
	 */
	@Override
	public void initializeView(){
		ActionListener[] listeners = new ActionListener[]{new ShowTracksListener(), new ShowPLListener(),
				new TrackAdderListener(), new PLAdderListener(), new DeleteListener()};
		this.mainView.addListeners(listeners);
		showTracks();
		this.mainView.initialize();
	}
	
	/**
	 * tells the DataController to show the tracks table delivering the required infos
	 */
	private void showTracks(){
		try {
			dataController.showTracksTable(getTracksToShow());
			mainView.setDataTitle("Tracce");
		} catch (ClassNotFoundException | IOException e) {
			dataController.showTracksTable(new LinkedHashMap<>());
			e.printStackTrace();
		}
	}
	
	/**
	 * tells the DataController to show the playlists table delivering the required infos
	 */
	private void showPlaylists(){
		try{
			dataController.showPLTable(getPlaylistsToShow());
			mainView.setDataTitle("Playlist");
		}catch(ClassNotFoundException | IOException e){
			dataController.showPLTable(new ArrayList<>());
		}
		
	}
	
	/**
	 * retieves a Map from the manager containing the correct track infos to show
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private Map<String, Float> getTracksToShow() throws FileNotFoundException, ClassNotFoundException, IOException{
		Map<String, Float> retMap = new LinkedHashMap<>();
		trackManager.retrieveOrdered().forEach(e-> retMap.put(e.getName(), e.getDuration()));
		return retMap;
	}
	
	/**
	 * retrieves a List from the manager containing the correct playlists info to show
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private List<String> getPlaylistsToShow() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<String> retList = new ArrayList<>();
		plManager.retrieveOrdered().forEach(e->retList.add(e.getName()));
		return new ArrayList<>(retList);
	}
	
	/**
	 * This listener handles the button "Le mie tracce" showing the user tracks throught
	 * a DataController call
	 * @author Francesco
	 *
	 */
	private class ShowTracksListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
				showTracks();
		}
	}
	
	/**
	 * This listener handles the button "Le mie Playlists" showing the user playlists throght
	 * a DataController call
	 * @author Francesco
	 *
	 */
	private class ShowPLListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showPlaylists();
		}
	}
	
	/**
	 * This listener calls the AddController to show a dialog to save a new track
	 * @author Francesco
	 *
	 */
	private class TrackAdderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				addCtrl.showTrackAdder();
				showTracks();
		}
	}
	
	/**
	 * This listener calls the AddController to show a dialog to save a new playlist
	 * @author Francesco
	 *
	 */
	private class PLAdderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				addCtrl.showPLAdder();
		}
	}
	
	/**
	 * This listener handles the deletion of the currently selected element
	 * @author Francesco
	 *
	 */
	private class DeleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String toDelete = dataController.getSelected();
			String currentlyShown = dataController.getCurrentView();
			if(currentlyShown.equals(DataControllerImpl.TRACKSVIEW)){
				trackManager.delete(toDelete);
				try {
					plManager.update(toDelete, null);
				} catch (ClassNotFoundException | IOException | UnsupportedAudioFileException e) {
					mainView.showErrorMessage("Qualcosa non va..", "Non è stato possibile aggiornare le playlist");
				}
				showTracks();
			}
			else if(currentlyShown.equals(DataControllerImpl.PLAYLISTSVIEW)){
				plManager.delete(toDelete);
				showPlaylists();
			}
		}
	}
	
	/**
	 * This Adapter handles the double click of an element to play it
	 * @author Francesco
	 *
	 */
	private class DoubleClickAdapter extends MouseAdapter{
		
		@Override
		public void mousePressed(MouseEvent me){
			JTable table =(JTable) me.getSource();
		    Point p = me.getPoint();
		    int row = table.rowAtPoint(p);
		    if (me.getClickCount() == 2) {
		    	String current = dataController.getCurrentView();
		        String selected = (String) table.getValueAt(row, 0);
		        if(current.equals(DataControllerImpl.PLAYLISTSVIEW)){
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
