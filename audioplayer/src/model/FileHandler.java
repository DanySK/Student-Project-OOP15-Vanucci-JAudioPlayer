package model;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class FileHandler {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String SEPARATOR = System.getProperty("file.separator");
	
	private static final String MAIN_DIR = HOME+SEPARATOR+"AudioPlayer";

	public static InputStream getFile(String fileAbsPath) throws FileNotFoundException{
		return new BufferedInputStream(new FileInputStream(new File(fileAbsPath)));
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
