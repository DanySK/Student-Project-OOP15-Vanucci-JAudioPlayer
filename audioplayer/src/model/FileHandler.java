package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface FileHandler {

	InputStream getFile(String fileName) throws FileNotFoundException;
	
	List<File> getFiles(String dir);

	OutputStream toFile(String fileName) throws FileNotFoundException;

	String getMainDir();

	void makeMainDir();

	String getSysSeparator();
}
