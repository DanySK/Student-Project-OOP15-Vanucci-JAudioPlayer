package model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileHandlerImpl {
	
	private static final String HOME = System.getProperty("user.home");
	private static final String SEPARATOR = System.getProperty("file.separator");
	
	private static final String MAIN_DIR = HOME+SEPARATOR+"AudioPlayer";
	
	private File target;
	
	public FileHandlerImpl(String userPath){
		
		this.target = new File(userPath);
	}

	/**
	 * 
	 * @param fileAbsPath the file's path
	 * 
	 * @return An InputStream for the required file
	 * @throws FileNotFoundException
	 */
	public InputStream getFile(String fileName) throws FileNotFoundException{
		return new BufferedInputStream(new FileInputStream(this.target+SEPARATOR+fileName));
	}
	
	public OutputStream toFile(String fileName) throws FileNotFoundException{
		return new FileOutputStream(this.target+SEPARATOR+fileName);
	}
	
	public boolean userExists(String username, String password) throws FileNotFoundException, IOException{
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getFile(MAIN_DIR+SEPARATOR+"users.txt")))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        if(line.contains("username: "+username) && line.contains("password: "+password)){
		        	if(!new File(MAIN_DIR+SEPARATOR+username).exists()){
		        		new File(MAIN_DIR+SEPARATOR+username).mkdir();
		        	}
		        	return true;
		        }
		    }
		    return false;
		}
	}
	
	public boolean trackExists(String name){
		
		for(final File track: getFiles()){
			System.out.println(track.getName());
			if(track.getName().equals(name))
				return true;
		}
		
		return false;
	}
	
	public List<File> getFiles(){
		
		return new ArrayList<>(Arrays.asList(target.listFiles()));
	}
	
	public void deleteFile(String trackFile){
		
		new File(trackFile).delete();
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
