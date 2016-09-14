package view.tables;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.OptionsManager;

public class TablesPane extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812705252648054415L;
	
	private OptionsManager manager;
	
	private JTable tracksTable;
	private JTable playlistsTable;
	
	private Map<String, Float> tracksData;

	public TablesPane(int vsbPolicy, int hsbPolicy, OptionsManager manager){
		super(vsbPolicy, hsbPolicy);
		tracksTable.setModel(createModel("Nome", "Durata"));
		playlistsTable.setModel(createModel("Nome"));
		this.manager = manager;
	}
	
	public void showTracks() throws FileNotFoundException, ClassNotFoundException, IOException{
		
		tracksData = manager.getTracks();
		DefaultTableModel model = (DefaultTableModel) tracksTable.getModel();
		Set<Entry<String, Float>> entrySet = tracksData.entrySet();
		
		for(Entry<String, Float> entry: entrySet){
			
			String[] newRow = {entry.getKey(), entry.getValue().toString()};
			model.addRow(newRow);
		}
		
		setViewPort(this.tracksTable);
	}
	
	private void setViewPort(JTable toView){
		this.setViewportView(toView);
	}
	
	private DefaultTableModel createModel(String... strings){
		DefaultTableModel model = new DefaultTableModel();
		
		for(int i = 0; i < strings.length; i++){
			model.addColumn(strings[i]);
		}
		
		return model;
	}
}
