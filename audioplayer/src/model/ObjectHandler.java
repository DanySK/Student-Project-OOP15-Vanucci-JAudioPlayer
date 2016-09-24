package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class extends the FileHandler class and gives static methods to retrieve and store object files
 * @author Francesco
 *
 */
public class ObjectHandler extends FileHandler{

	public static void objectToFile(Object toWrite, String objPath) throws FileNotFoundException, IOException{
		ObjectOutputStream writer = new ObjectOutputStream(toFile(objPath));
		writer.writeObject(toWrite);
		writer.close();
	}
	
	public static Object fileToObject(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream reader = new ObjectInputStream(getFile(filePath));
		Object retrieved = reader.readObject();
		reader.close();
		return retrieved;
	}
}
