package goodfeeling.db;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public interface InputOutput {

	public InputStream getInputStream(String name)
	throws FileNotFoundException;

	public OutputStream getOutputStream(String name)
	throws FileNotFoundException;
}
