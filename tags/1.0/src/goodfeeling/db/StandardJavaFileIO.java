package goodfeeling.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StandardJavaFileIO implements InputOutput {

	@Override
	public InputStream getInputStream(String name) throws FileNotFoundException {
		return new FileInputStream(name);
	}

	@Override
	public OutputStream getOutputStream(String name)
	throws FileNotFoundException {
		return new FileOutputStream(name);
	}
}
