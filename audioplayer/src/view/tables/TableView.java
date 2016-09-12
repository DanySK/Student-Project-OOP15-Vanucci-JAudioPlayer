package view.tables;

import java.util.Arrays;
import java.util.List;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableView extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6812705252648054415L;
	
	private JTable tracksTable;
	private JTable playlistsTable;

	public TableView(int vsbPolicy, int hsbPolicy){
		
		super(vsbPolicy, hsbPolicy);
		List<String> columns = new ArrayList<>(Arrays.asList(new String[]{"Titolo1", "Titolo2", "Titolo3"}));
		List<String[]> dataList = new ArrayList<>();
		for(int i = 1; i < 30; i++){
		
		dataList.add(new String[]{"firstInfo "+i, "secondInfo "+i, "ThirdInfo "+i});
		}
		JTable table = new JTable(dataList.toArray(new String[][]{}), columns.toArray());
		table.setEnabled(false);
		this.add(table);
		this.setViewportView(table);
	}
}
