package view;

import java.awt.event.ActionListener;
import view.create.PlaylistAdder;
import view.create.TrackAdder;
import view.data.DataPane;
import view.player.Player;

public interface AudioPlayerGUI {

	public void initialize();
	
	void showErrorMessage(String title, String message);

	void addListeners(ActionListener[] listeners);

	TrackAdder getTrackAdder();

	PlaylistAdder getPLAdder();
	
	DataPane getDataPane();
	
	Player getPlayer();

	void setDataTitle(String title);
}
