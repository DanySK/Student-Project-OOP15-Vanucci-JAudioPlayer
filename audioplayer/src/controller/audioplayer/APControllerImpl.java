package controller.audioplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import controller.DataController;
import controller.adder.*;
import controller.player.PlayerController;
import model.PlaylistManager;
import model.TrackManager;
import model.user.User;
import view.AudioPlayerGUI;
import view.AudioPlayerImpl;
import view.create.PlaylistAdder;
import view.create.TrackAdder;

public class APControllerImpl {

	private AudioPlayerGUI mainView;
	private DataController dataController;
	private AddController addCtrl;
//	private PlayerController playerCtrl;
	
	public APControllerImpl(User logged){
		this.mainView = new AudioPlayerImpl();
		TrackManager trackManager = new TrackManager(logged.getUsername());
		PlaylistManager plManager = new PlaylistManager(logged.getUsername());
		this.dataController = new DataController(trackManager, plManager, mainView.getDataPane());
		this.addCtrl = new AddControllerImpl(trackManager, plManager, mainView.getTrackAdder(), mainView.getPLAdder());
//		this.playerCtrl = playerCtrl;
	}
	
	public void initializeView(){
		ActionListener[] listeners = new ActionListener[]{new ShowTracksListener(), new ShowPLListener(),
				new TrackAdderListener()};
		this.mainView.addListeners(listeners);
		resetTracks();
		this.mainView.initialize();
	}
	
	private void resetTracks(){
		try {
			this.mainView.showTracks(dataController.getTracks());
		} catch (ClassNotFoundException | IOException e) {
			mainView.showErrorMessage("Qualcosa è andato storto", "Impossibile recuperare i brani");
			e.printStackTrace();
		}
	}
	
	private class ShowTracksListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
				resetTracks();
		}
	}
	
	private class ShowPLListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				List<String> plInfos = dataController.getPlaylists();
				mainView.showPlaylists(plInfos);
			} catch (ClassNotFoundException | IOException e) {
				mainView.showErrorMessage("Qualcosa è andato storto", "Impossibile recuperare i brani");
			}
		}
	}
	
	private class TrackAdderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				addCtrl.showTrackAdder();
				System.out.println("Resetto le tracce");
				resetTracks();
		}
	}
}
