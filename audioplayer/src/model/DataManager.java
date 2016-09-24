package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.sound.sampled.UnsupportedAudioFileException;

public interface DataManager<T>{

	T retrieve(String name) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	List<T> retrieveAll() throws FileNotFoundException, ClassNotFoundException, IOException;
	
	Set<T> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException;
	
	void addNew(T toAdd) throws FileNotFoundException, IOException, ClassNotFoundException;
	
	void delete(String toDelete);
	
	void update(String name, String file) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException;
}
