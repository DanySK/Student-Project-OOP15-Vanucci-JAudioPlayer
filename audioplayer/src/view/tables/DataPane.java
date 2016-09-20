package view.tables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.DataManager;

public class DataPane extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812705252648054415L;
	
	private static final String[] TRACK_COLUMNS = {"Nome", "Durata"};
	private static final String[] PLAYLIST_COLUMNS = {"Nome"};
	private static final String TRACKSTABLE_ID = "Tracks";
	private static final String PLTABLE_ID = "Playlists";
	
	private final JTable tracksTable = new JTable();
	private final JTable playlistsTable = new JTable();
	private DataManager manager;
	private Map<String, Float> tracksData = new HashMap<>();
	private List<String> plData = new ArrayList<>();
	private String currView;

	public DataPane(int vsbPolicy, int hsbPolicy, DataManager manager){
		super(vsbPolicy, hsbPolicy);
		tracksTable.setModel(createModel(TRACK_COLUMNS));
		playlistsTable.setModel(createModel(PLAYLIST_COLUMNS));
		this.manager = manager;
	}
	
	public void showTracks() throws FileNotFoundException, ClassNotFoundException, IOException{
		this.tracksData = this.manager.getTracks();
		DefaultTableModel model = (DefaultTableModel) tracksTable.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
		Set<Entry<String, Float>> entrySet = new TreeSet<>((e1, e2)-> e1.getKey().compareTo(e2.getKey()));
		entrySet.addAll(tracksData.entrySet());
		
		for(Entry<String, Float> entry: entrySet){
			String[] newRow = {entry.getKey(), formatDuration(entry.getValue())};
			model.addRow(newRow);
		}
//		setViewPort(this.tracksTable);
		setCurrentView(TRACKSTABLE_ID);
	}
	
	public void showPlaylists() throws FileNotFoundException, ClassNotFoundException, IOException{
		this.plData = this.manager.getPlaylists();
		DefaultTableModel model = (DefaultTableModel) playlistsTable.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
		for(String playlist: plData){
			model.addRow(new String[]{playlist});
		}
//		setViewPort(this.playlistsTable);
		setCurrentView(PLTABLE_ID);
	}
	
	private String formatDuration(Float duration){
		Long lDuration = duration.longValue();
		return String.format("%d:%02d:%02d", lDuration / 3600,
										(lDuration % 3600) / 60, (lDuration % 60));
	}
	
//	private void setViewPort(JTable toView){
//		this.setViewportView(toView);
//	}
	
	private DefaultTableModel createModel(String... strings){
		DefaultTableModel model = new DefaultTableModel(){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 5051630758412313262L;

			@Override
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		List<String> columnList = Arrays.asList(strings);
		columnList.forEach(e-> model.addColumn(e));
		
		return model;
	}
	
	public String getCurrentView(){
		return new String(this.currView);
	}
	
	private void setCurrentView(String viewID){
		this.currView = viewID;
		switch (viewID){
			case TRACKSTABLE_ID: 
				this.setViewportView(this.tracksTable);
				break;
			case PLTABLE_ID:
				this.setViewportView(this.playlistsTable);
				break;
		}
	}
	
	public void setAdapter(MouseAdapter adapter){
		this.tracksTable.addMouseListener(adapter);
		this.playlistsTable.addMouseListener(adapter);
	}
}
