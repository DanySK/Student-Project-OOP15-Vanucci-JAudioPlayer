package view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import view.create.PlaylistAdder;
import view.create.TrackAdder;
import view.data.DataPane;
import view.data.DataPaneImpl;
import view.player.Player;

public interface AudioPlayerGUI {

	public void initialize();

//	void showTracks(Map<String, Float> tracksInfos);
//
//	void showPlaylists(List<String> plInfos);
	
	void showErrorMessage(String title, String message);

	void addListeners(ActionListener[] listeners);

//	void showTrackAdder();
//
//	void showPLAdder();

	TrackAdder getTrackAdder();

	PlaylistAdder getPLAdder();
	
	DataPane getDataPane();
	
	Player getPlayer();

	void setDataTitle(String title);
}
