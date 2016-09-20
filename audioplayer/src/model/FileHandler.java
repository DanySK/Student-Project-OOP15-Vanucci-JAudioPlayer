package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileHandler {

	InputStream getFile(String fileName) throws FileNotFoundException;

	OutputStream toFile(String fileName) throws FileNotFoundException;

	String getMainDir();

	void makeMainDir();
}
