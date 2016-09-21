package view.tables;

import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataPane {

	void showTracks(Map<String, Float> tracksInfos) throws FileNotFoundException, ClassNotFoundException, IOException;

	void showPlaylists(List<String> plInfos) throws FileNotFoundException, ClassNotFoundException, IOException;

	String getCurrentView();

	void setAdapter(MouseAdapter adapter);

}
