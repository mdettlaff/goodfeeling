package goodfeeling.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public interface FileIO {

	public FileInputStream getFileInputStream(String name)
	throws FileNotFoundException;

	public FileOutputStream getFileOutputStream(String name)
	throws FileNotFoundException;
}
