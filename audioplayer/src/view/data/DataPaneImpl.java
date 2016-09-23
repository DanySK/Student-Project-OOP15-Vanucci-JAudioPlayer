package view.data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.MouseAdapter;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DataPaneImpl extends JScrollPane implements DataPane{

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
	private String currView;

	public DataPaneImpl(int vsbPolicy, int hsbPolicy){
		super(vsbPolicy, hsbPolicy);
		tracksTable.setModel(createModel(TRACK_COLUMNS));
		playlistsTable.setModel(createModel(PLAYLIST_COLUMNS));
	}
	
	@Override
	public void showTracks(Map<String, Float> tracksInfos){
		DefaultTableModel model = (DefaultTableModel) tracksTable.getModel();
		prepareModel(model);
		for(Entry<String, Float> entry: tracksInfos.entrySet()){
			String[] newRow = {entry.getKey(), formatDuration(entry.getValue())};
			model.addRow(newRow);
		}
		setCurrentView(TRACKSTABLE_ID);
	}
	
	@Override
	public void showPlaylists(List<String> plInfos){
		System.out.println("Sono nel DataPaneImpl. showPlaylists, prima di settare il model :"+new File("C:/Users/Francesco/AudioPlayer/user1/Playlists/Funziona.dat").canWrite());
		DefaultTableModel model = (DefaultTableModel) playlistsTable.getModel();
		prepareModel(model);
		for(String playlist: plInfos){
			model.addRow(new String[]{new String(playlist)});
		}
		System.out.println("Sono nel DataPaneImpl.showPlaylists, ho appena popolato il model :"+new File("C:/Users/Francesco/AudioPlayer/user1/Playlists/Funziona.dat").canWrite());
		setCurrentView(PLTABLE_ID);
	}
	
	private void prepareModel(DefaultTableModel model){
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
	}
	
	@Override
	public void showErrorMessage(String title, String message){
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	private String formatDuration(Float duration){
		Long lDuration = duration.longValue();
		return String.format("%d:%02d:%02d", lDuration / 3600,
										(lDuration % 3600) / 60, (lDuration % 60));
	}
	
	@Override
	public String getSelectedString(){
		JTable currentTable = null;
		int column = 0;
		if(currView.equals(TRACKSTABLE_ID)){
			currentTable = tracksTable;
		}
		if(currView.equals(PLTABLE_ID)){
			currentTable = playlistsTable;
		}
		int row = currentTable.getSelectedRow();
		return new String((String)currentTable.getModel().getValueAt(row, column));
	}
	
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
	
	@Override
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
	
	@Override
	public void setAdapter(MouseAdapter adapter){
		this.tracksTable.addMouseListener(adapter);
		this.playlistsTable.addMouseListener(adapter);
	}
}
