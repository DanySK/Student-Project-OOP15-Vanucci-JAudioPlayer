package model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class TextFileHandler extends FileHandlerImpl{

	@Override
	public boolean getFile(String) throws FileNotFoundException{
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getFile("users.txt")))) {
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
	
	@Override
	public OutputStream toFile(String fileName) throws FileNotFoundException{
		return new FileOutputStream(fileName);
	}
}
