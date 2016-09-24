package model;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileHandlerImpl implements FileHandler{
	
	protected static final String HOME = System.getProperty("user.home");
	protected static final String SEPARATOR = System.getProperty("file.separator");
	protected static final String MAIN_DIR = HOME+SEPARATOR+"AudioPlayer";

	/**
	 * 
	 * @param fileAbsPath the file's path
	 * 
	 * @return An InputStream for the required file
	 * @throws FileNotFoundException
	 */
	@Override
	public InputStream getFile(String fileName) throws FileNotFoundException{
		return new BufferedInputStream(new FileInputStream(MAIN_DIR+SEPARATOR+fileName));
	}
	
	@Override
	public OutputStream toFile(String fileName) throws FileNotFoundException{
		return new FileOutputStream(MAIN_DIR+SEPARATOR+fileName);
	}
	
	@Override
	public List<File> getFiles(String dir) {
		System.out.println(dir);
		return Arrays.asList(new File(MAIN_DIR+SEPARATOR+dir).listFiles());
	}
	
	public boolean deleteFile(String relPath){
		System.out.println("Removing: "+MAIN_DIR+SEPARATOR+relPath);
		File deleting = new File(MAIN_DIR+SEPARATOR+relPath);
		boolean exist = deleting.exists();
		boolean result = deleting.delete();
		System.out.println("Esiste? "+exist+", eliminato? "+result);
		return result;
	}
	
	@Override
	public String getMainDir(){
		return new String(MAIN_DIR+SEPARATOR);
	}
	
	@Override
	public void makeMainDir(){
		
		makeDir(new String(""));
	}
	
	@Override
	public void makeDir(String dirPath){
		String completePath = MAIN_DIR+SEPARATOR+dirPath;
		if(Files.notExists(Paths.get(completePath), LinkOption.NOFOLLOW_LINKS)){
			System.out.println("Tentando di creare la cartella a: "+completePath);
			new File(completePath).mkdir();
		}
	}
	
	@Override
	public String getSysSeparator(){
		return System.getProperty("file.separator");
	}
}
