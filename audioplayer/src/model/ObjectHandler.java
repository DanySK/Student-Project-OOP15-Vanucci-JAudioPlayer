package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectHandler extends FileHandlerImpl{

	public void objectToFile(Object toWrite, String objPath) throws FileNotFoundException, IOException{
		ObjectOutputStream writer = new ObjectOutputStream(super.toFile(objPath));
		writer.writeObject(toWrite);
		writer.close();
	}
	
	public Object fileToObject(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream reader = new ObjectInputStream(super.getFile(filePath));
		Object retrieved = reader.readObject();
		reader.close();
		return retrieved;
	}
}
