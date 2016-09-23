package view.data;

import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataPane {

	void showTracks(Map<String, Float> tracksInfos);

	void showPlaylists(List<String> plInfos);

	String getCurrentView();

	void setAdapter(MouseAdapter adapter);

	void showErrorMessage(String title, String message);

	String getSelectedString();

}
