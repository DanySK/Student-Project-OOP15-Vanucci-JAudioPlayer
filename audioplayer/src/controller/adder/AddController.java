package controller.adder;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;

public interface AddController {

	void saveTrack(String trackName, String trackFile) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException;

	void showTrackAdder();

	void showPLAdder();

	void showDialog(JDialog toShow);

}
