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
	
	@Override
	public String getMainDir(){
		return new String(MAIN_DIR+SEPARATOR);
	}
	
	@Override
	public void makeMainDir(){
		
		if(Files.notExists(Paths.get(MAIN_DIR), LinkOption.NOFOLLOW_LINKS)){
			
			new File(MAIN_DIR).mkdir();
		}
	}
	
	@Override
	public String getSysSeparator(){
		return System.getProperty("file.separator");
	}
}
