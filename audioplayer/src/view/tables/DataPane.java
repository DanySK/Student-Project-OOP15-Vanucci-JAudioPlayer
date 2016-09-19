package view.tables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.OptionsManager;

public class DataPane extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812705252648054415L;
	
	private final JTable tracksTable = new JTable();
	private final JTable playlistsTable = new JTable();
	private OptionsManager manager;
	private Map<String, Float> tracksData = new HashMap<>();
	private List<String> plData = new ArrayList<>();

	public DataPane(int vsbPolicy, int hsbPolicy, OptionsManager manager){
		super(vsbPolicy, hsbPolicy);
		tracksTable.setModel(createModel("Nome", "Durata"));
		playlistsTable.setModel(createModel("Nome"));
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
		setViewPort(this.tracksTable);
	}
	
	public void showPlaylists() throws FileNotFoundException, ClassNotFoundException, IOException{
		this.plData = this.manager.getPlaylists();
		DefaultTableModel model = (DefaultTableModel) playlistsTable.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
		for(String playlist: plData){
			model.addRow(new String[]{playlist});
		}
		setViewPort(this.playlistsTable);
	}
	
	private String formatDuration(Float duration){
		Long lDuration = duration.longValue();
		return String.format("%d:%02d:%02d", lDuration / 3600,
										(lDuration % 3600) / 60, (lDuration % 60));
	}
	
	private void setViewPort(JTable toView){
		this.setViewportView(toView);
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
	
	public void setAdapter(MouseAdapter adapter){
		this.tracksTable.addMouseListener(adapter);
		this.playlistsTable.addMouseListener(adapter);
	}
}
