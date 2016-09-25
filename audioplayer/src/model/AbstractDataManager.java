package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is an abstract class defining the DataManager, it's specialized by the TrackManager and 
 * the PlaylistManager
 * @author Francesco
 *
 * @param <T>
 */
public abstract class AbstractDataManager<T> implements DataManager<T>{

	protected static final String EXTENSION = ".dat";
	
	protected List<T> stored = null;
	protected String fileSeparator;
	protected String dirPath;
	
	public AbstractDataManager(String username, String thisDir){
		this.fileSeparator = FileHandler.getSysSeparator();
		this.dirPath = username+fileSeparator+thisDir;
		
	}
	
	/**
	 * retrieves an object defined by the relative path in the main dir
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T retrieve(String name) throws FileNotFoundException, ClassNotFoundException, IOException {
		return (T)ObjectHandler.fileToObject(dirPath+fileSeparator+name+EXTENSION);
	}

	/**
	 * Retrieves all the objects in the specified directory
	 */
	@Override
	public List<T> retrieveAll() throws FileNotFoundException, ClassNotFoundException, IOException {
		if(stored != null){
			return new ArrayList<>(stored);
		}
		List<T> results = new ArrayList<>();
		FileHandler.makeDir(dirPath);
		for(File file : FileHandler.getFiles(dirPath)){
			results.add(retrieve(file.getName().replace(EXTENSION, "")));
		}
		this.stored = results;
		return results;
	}

	/**
	 * Retrieves all the object sorting the result
	 */
	@Override
	public abstract Set<T> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException;

	/**
	 * This is an abstract method for adding an object
	 */
	@Override
	public abstract void addNew(T toAdd) throws FileNotFoundException, IOException, ClassNotFoundException;

	/**
	 * This is an abstract method for deleting an object
	 */
	@Override
	public abstract void delete(String toDelete);

	/**
	 * This is an abstract method for updating an object
	 */
	@Override
	public abstract void update(String name, String file) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException;

}
