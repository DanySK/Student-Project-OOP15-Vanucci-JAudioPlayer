package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

import model.Playlist;
import model.PlaylistManager;
import model.Track;
import model.TrackImpl;
import model.TrackManager;
import model.user.User;
import view.tables.DataPane;

public class DataController {

	private TrackManager trackManager;
	private PlaylistManager plManager;
	private DataPane dataPane;
	
	public DataController(TrackManager trackManager, PlaylistManager plManager, DataPane dataPane){
		this.trackManager = trackManager;
		this.plManager = plManager;
		this.dataPane = dataPane;
	}
	
//	public void addTrack(final String filePath, final String name) throws UnsupportedAudioFileException, IOException, ClassNotFoundException{
//		File file = new File(filePath);
//		Track toAdd = new TrackImpl(file, name);
//		trackManager.addTrack(toAdd);
//	}
//	
//	public void createPlaylist(String plName, List<String> trackNames) throws FileNotFoundException, ClassNotFoundException, IOException{
//		plManager.create(plName, trackNames);
//	}
//	
//	public void showTracks(){
//		
//	}
	
	public Map<String, Float> getTracks() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Track> sorted = new TreeSet<>((t1, t2)->t1.getName().compareTo(t2.getName()));
		System.out.println(sorted.toString());
		sorted.addAll(trackManager.retrieveAll());
		Map<String, Float> retMap = new HashMap<>();
		sorted.forEach(e-> retMap.put(e.getName(), e.getDuration()));
		return retMap;
	}
	
	public List<String> getPlaylists() throws FileNotFoundException, ClassNotFoundException, IOException{
		Set<Playlist> sorted = new TreeSet<>((t1, t2)->t1.getName().compareTo(t2.getName()));
		sorted.addAll(plManager.retrieveAll());
		System.out.println(sorted.toString());
		List<String> retList = new ArrayList<>();
		sorted.forEach(e->retList.add(e.getName()));
		return retList;
	}
	
//	public String getTrackPath(String trackName){
//		String retString = null;
//		for(Track track: userTracks){
//			if(track.getName().equals(trackName))
//				retString = track.getFilePath();
//		}
//		return retString;
//	}
	
//	public Playlist getPlaylist(String plName){
//		for(Playlist pl: userPlaylists){
//			if(pl.getName().equals(plName)){
//				return pl;
//			}
//		}
//		return null;
//	}
	
	private class DoubleClickAdapter extends MouseAdapter{
	
		@Override
		public void mousePressed(MouseEvent me){
			JTable table =(JTable) me.getSource();
		    Point p = me.getPoint();
		    int row = table.rowAtPoint(p);
		    if (me.getClickCount() == 2) {
		    	String current = dataPane.getCurrentView();
		        String selected = (String) table.getValueAt(row, 0);
//		       if(current.equals("Playlists")){
//		           	
//		           }else{
//		            	player.openFile(selected, manager.getTrackPath(selected));
//		            }
//		            
		        }
			}
		}
}
