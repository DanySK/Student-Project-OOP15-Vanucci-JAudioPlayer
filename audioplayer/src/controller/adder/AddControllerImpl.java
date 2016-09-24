package controller.adder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.PlaylistImpl;
import model.PlaylistManager;
import model.Track;
import model.TrackImpl;
import model.TrackManager;
import view.create.PlaylistAdder;
import view.create.TrackAdder;

public class AddControllerImpl implements AddController{

	private TrackAdder trackAdder;
	private PlaylistAdder plAdder;
	
	private TrackManager trackManager;
	private PlaylistManager plManager;
	
	public AddControllerImpl(TrackManager trackManager, PlaylistManager plManager, final TrackAdder trackAdder, final PlaylistAdder plAdder){
		this.trackAdder = trackAdder;
		this.plAdder = plAdder;
		this.trackManager = trackManager;
		this.plManager = plManager;
		initializeButtons();
	}
	
	private void initializeButtons(){
		trackAdder.setButtons(new TrackAdderListener(), new ChooserListener());
		plAdder.setButtons(new PlaylistAdderListener());
	}
	
	@Override
	public void showTrackAdder(){
		showDialog(trackAdder);
	}
	
	@Override
	public void showPLAdder(){
		try {
			List<String> sortedNames = new LinkedList<>();
			trackManager.retrieveOrdered().forEach(e->sortedNames.add(e.getName()));
			plAdder.refreshList(sortedNames);
			showDialog(plAdder);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	@Override
	public void savePlaylist(String plName, List<Track> plTracks) throws FileNotFoundException, ClassNotFoundException, IOException {
		plManager.addPlaylist(new PlaylistImpl(plName, plTracks));
	}
	
	@Override
	public boolean checkString(String toCheck){
		return toCheck.trim().length() <= 0;
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
			if(checkString(trackName) || checkString(trackFile)){
				trackAdder.showErrorMessage("Dati errati", "E' necessario scegliere un nome e un file");
			}
			else{
				try {
					saveTrack(trackName, trackFile);
				} catch (ClassNotFoundException | IOException | UnsupportedAudioFileException e1) {
					trackAdder.showErrorMessage("Qualcosa non va", "Controlla i dati inseriti");
				} catch (IllegalArgumentException ex){
					int result = trackAdder.showConfirmMessage("Il brano esiste già", "Vuoi sovrascrivere il brano esistente?");
					if(result == JOptionPane.YES_OPTION){
						try {
							trackManager.updateTrack(trackName, trackFile);
							plManager.updatePlaylists(trackName, trackFile);
						} catch (ClassNotFoundException | IOException | UnsupportedAudioFileException e1) {
							trackAdder.showErrorMessage("Qualcosa è andato storto...", "Impossibile sostituire il brano");
						}
					}
				}finally{
					trackAdder.setVisible(false);
				}
			}
		}
	}
	
	private class PlaylistAdderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String plName = plAdder.getInputName();
			List<String> selected = plAdder.getSelected();
			if(checkString(plName) || selected.isEmpty()){
				plAdder.showErrorMessage("Dati errati", "E' necessario scegliere un nome e almeno un brano");
			}else{
				List<Track> plTracks = new ArrayList<>();
				try{
					for(String name: selected){
						plTracks.add(trackManager.retrieve(name));
					}
					savePlaylist(plName, plTracks);
					plAdder.setVisible(false);
				}catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
			}
		}	
	}
}
