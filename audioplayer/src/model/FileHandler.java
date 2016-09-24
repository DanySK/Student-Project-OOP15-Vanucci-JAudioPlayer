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

	
	public static InputStream getFile(String fileName) throws FileNotFoundException{
		return new BufferedInputStream(new FileInputStream(MAIN_DIR+SEPARATOR+fileName));
	}
	
	public static OutputStream toFile(String fileName) throws FileNotFoundException{
		return new FileOutputStream(MAIN_DIR+SEPARATOR+fileName);
	}
	
	public static List<File> getFiles(String dir) {
		System.out.println(dir);
		return Arrays.asList(new File(MAIN_DIR+SEPARATOR+dir).listFiles());
	}
	
	public static boolean deleteFile(String relPath){
		System.out.println("Removing: "+MAIN_DIR+SEPARATOR+relPath);
		File deleting = new File(MAIN_DIR+SEPARATOR+relPath);
		boolean exist = deleting.exists();
		boolean result = deleting.delete();
		System.out.println("Esiste? "+exist+", eliminato? "+result);
		return result;
	}
	
	public static String getMainDir(){
		return new String(MAIN_DIR+SEPARATOR);
	}
	
	public static void makeMainDir(){
		
		makeDir(new String(""));
	}
	
	public static void makeDir(String dirPath){
		String completePath = MAIN_DIR+SEPARATOR+dirPath;
		if(Files.notExists(Paths.get(completePath), LinkOption.NOFOLLOW_LINKS)){
			new File(completePath).mkdir();
		}
	}
	
	public static String getSysSeparator(){
		return System.getProperty("file.separator");
	}
}
