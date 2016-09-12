package view.tables;

import java.util.List;

public interface Table {

	
	/**
	 * Sets the columns of the table
	 * @param titles the names of the table columns
	 * 
	 */
	void setColumns(List<String> titles);
	
	/**
	 * Populates the table with the informations required (as String objects)
	 * @param data the string rapresentation of the objects infos
	 */
	void populate(List<String> data);
}
