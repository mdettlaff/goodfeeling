package goodfeeling.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class StandardJavaFileIO implements FileIO {

	@Override
	public FileInputStream getFileInputStream(String name)
	throws FileNotFoundException {
		return new FileInputStream(name);
	}

	@Override
	public FileOutputStream getFileOutputStream(String name)
	throws FileNotFoundException {
		return new FileOutputStream(name);
	}
}
