package controller.data;

import java.util.List;
import java.util.Map;

public interface DataController {

	/**
	 * returns the view currently visualized in the data pane
	 * @return the id name of the view currently shown
	 */
	String getCurrentView();
	
	/**
	 * refreshes and shows the table containing the user tracks
	 * @param tracksInfos the infos to show for every track
	 */
	void showTracksTable(Map<String, Float> tracksInfos);
	
	/**
	 * refreshes and shows the table containing the user playlists
	 * @param playlistsInfos the infos to show for every playlist
	 */
	void showPLTable(List<String> playlistsInfos);
	
	/**
	 * returns the track or playlist currently selected by the user
	 * @return the name of the selected element
	 */
	String getSelected();
	
	/**
	 * shows an error message if nothing has been found
	 * @param message the message to show
	 */
	void nothingFound(String message);
}
