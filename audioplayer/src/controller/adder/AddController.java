package controller.adder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;

import model.Track;

public interface AddController {

	void saveTrack(String trackName, String trackFile) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException;

	void showTrackAdder();

	void showPLAdder();

	void showDialog(JDialog toShow);

	boolean checkString(String toCheck);

	void savePlaylist(String plName, List<Track> plTracks) throws FileNotFoundException, ClassNotFoundException, IOException;

}
