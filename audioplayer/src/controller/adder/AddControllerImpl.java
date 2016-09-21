package controller.adder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;

import controller.DataController;
import model.PlaylistManager;
import model.TrackImpl;
import model.TrackManager;
import view.create.PlaylistAdder;
import view.create.TrackAdder;

public class AddControllerImpl implements AddController{

	private TrackAdder trackAdder;
	private PlaylistAdder plAdder;
	
	protected TrackManager trackManager;
	protected PlaylistManager plManager;
	
	public AddControllerImpl(TrackManager trackManager, PlaylistManager plManager, final TrackAdder trackAdder, final PlaylistAdder plAdder){
		this.trackAdder = trackAdder;
		this.plAdder = plAdder;
		this.trackManager = trackManager;
		this.plManager = plManager;
		initializeButtons();
	}
	
	private void initializeButtons(){
		trackAdder.setButtons(new TrackAdderListener(), new ChooserListener());
	}
	
	@Override
	public void showTrackAdder(){
		showDialog(trackAdder);
	}
	
	@Override
	public void showPLAdder(){
		showDialog(plAdder);
	}
	
	@Override
	public void showDialog(JDialog toShow){
		toShow.setVisible(true);
	}
	
	@Override
	public void saveTrack(String trackName, String trackFile) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException{
		System.out.println("Voglio salvare: "+trackName+", assegnata al file: "+trackFile);
		trackManager.addTrack(new TrackImpl(new File(trackFile), trackName));
	}
	
	private class ChooserListener implements ActionListener{
		
		private String lastPath = null;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.lastPath = trackAdder.chooseFile(lastPath);
		}
		
	}
	
	private class TrackAdderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String trackName = trackAdder.getInputName();
			String trackFile = trackAdder.getChosenFile();
			try {
				saveTrack(trackName, trackFile);
				trackAdder.setVisible(false);
			} catch (ClassNotFoundException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
