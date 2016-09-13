package model;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileHandler {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String SEPARATOR = System.getProperty("file.separator");
	
	private static final String MAIN_DIR = HOME+SEPARATOR+"AudioPlayer";
	
	private File target;
	
	public FileHandler(String fileAbsPath){
		
		this.target = new File(fileAbsPath);
	}

	/**
	 * 
	 * @param fileAbsPath the file's path
	 * 
	 * @return An InputStream for the required file
	 * @throws FileNotFoundException
	 */
	public InputStream getFile() throws FileNotFoundException{
		return new BufferedInputStream(new FileInputStream(this.target));
	}
	
	public OutputStream toFile() throws FileNotFoundException{
		return new FileOutputStream(this.target);
	}
	
	public boolean exists(){
		
		return this.target.exists();
	}
	/**
	 * Creates the main directory for the app
	 */
	public static void makeDir(){
		
		if(Files.notExists(Paths.get(MAIN_DIR), LinkOption.NOFOLLOW_LINKS)){
			
			new File(MAIN_DIR).mkdir();
		}
	}
	
	public static String getDir(){
		
		return new String(MAIN_DIR);
	}
}
