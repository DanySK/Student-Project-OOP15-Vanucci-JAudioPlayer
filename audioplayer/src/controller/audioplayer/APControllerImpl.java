package controller.audioplayer;

import model.PlaylistManager;
import model.TrackManager;
import model.user.User;
import view.AudioPlayerGUI;
import view.AudioPlayerImpl;

public class APControllerImpl {

	private AudioPlayerGUI mainView;
	private TrackManager trackManager;
	private PlaylistManager plManager;
	private User currentUser;
	
	public APControllerImpl(User current){
		this.currentUser = current;
		this.mainView = new AudioPlayerImpl();
	}
	
	public void initializeView(){
		this.mainView.initialize();
	}
	
	private class OptionsListener implements ActionListener{
		
	}
}
