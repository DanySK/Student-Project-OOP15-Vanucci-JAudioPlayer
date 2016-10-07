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
import java.io.IOException;

/**
 * This static class handles the informations regarding the main directory, the file separator used
 * by the system and the principal methods to access and retrieve a file
 * @author Francesco
 *
 */
public class FileHandler{
	
	protected static final String HOME = System.getProperty("user.home");
	protected static final String SEPARATOR = System.getProperty("file.separator");
	protected static final String MAIN_DIR = HOME+SEPARATOR+"AudioPlayer";

	/**
	 * get the specified file in the main directory, based on the relative path
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static InputStream getFile(String fileName) throws FileNotFoundException{
		return new BufferedInputStream(new FileInputStream(MAIN_DIR+SEPARATOR+fileName));
	}
	
	/**
	 * saves on a new file in the main directory defined by the relative path
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static OutputStream toFile(String fileName) throws FileNotFoundException{
		return new FileOutputStream(MAIN_DIR+SEPARATOR+fileName);
	}
	
	/**
	 * Gets all the files in a directory in the main directory
	 * @param dir
	 * @return
	 */
	public static List<File> getFiles(String dir) {
		System.out.println(dir);
		return Arrays.asList(new File(MAIN_DIR+SEPARATOR+dir).listFiles());
	}
	
	/**
	 * Deletes a file in the main directory, based on the relative path
	 * @param relPath
	 * @return
	 */
	public static boolean deleteFile(String relPath){
		File deleting = new File(MAIN_DIR+SEPARATOR+relPath);
		boolean result = deleting.delete();
		return result;
	}
	
	/**
	 * returns the main directory for the application
	 * @return
	 */
	public static String getMainDir(){
		return new String(MAIN_DIR+SEPARATOR);
	}
	
	/**
	 * Creates a main directory for the application if not already present
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void makeMainDir() throws FileNotFoundException, IOException{
		
		makeDir(new String(""));
	}
	
	/**
	 * Creates a specified directory inside the main directory based on the relative path
	 * @param dirPath
	 */
	public static void makeDir(String dirPath){
		String completePath = MAIN_DIR+SEPARATOR+dirPath;
		if(Files.notExists(Paths.get(completePath), LinkOption.NOFOLLOW_LINKS)){
			new File(completePath).mkdir();
		}
	}
	
	/**
	 * Returns the file.separator for this system
	 * @return
	 */
	public static String getSysSeparator(){
		return System.getProperty("file.separator");
	}
}
