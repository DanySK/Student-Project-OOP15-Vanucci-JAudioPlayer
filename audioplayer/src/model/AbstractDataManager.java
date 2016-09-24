package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class AbstractDataManager<T> implements DataManager<T>{

	protected static final String EXTENSION = ".dat";
	
	protected List<T> stored = null;
	protected String fileSeparator;
	protected String dirPath;
	
	public AbstractDataManager(String username, String thisDir){
		this.fileSeparator = FileHandler.getSysSeparator();
		this.dirPath = username+fileSeparator+thisDir;
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T retrieve(String name) throws FileNotFoundException, ClassNotFoundException, IOException {
		return (T)ObjectHandler.fileToObject(dirPath+fileSeparator+name+EXTENSION);
	}

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

	@Override
	public abstract Set<T> retrieveOrdered() throws FileNotFoundException, ClassNotFoundException, IOException;

	@Override
	public abstract void addNew(T toAdd) throws FileNotFoundException, IOException, ClassNotFoundException;

	@Override
	public abstract void delete(String toDelete);

	@Override
	public abstract void update(String name, String file) throws FileNotFoundException, ClassNotFoundException, IOException, UnsupportedAudioFileException;

}
