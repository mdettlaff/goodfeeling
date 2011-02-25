package goodfeeling.db;

import goodfeeling.db.InputOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class InMemoryIO implements InputOutput {

	private Map<String, ByteArrayOutputStream> fakeFilesystem;

	public InMemoryIO() {
		fakeFilesystem = new HashMap<String, ByteArrayOutputStream>();
	}

	@Override
	public InputStream getInputStream(String name) throws FileNotFoundException {
		if (!fakeFilesystem.containsKey(name)) {
			throw new FileNotFoundException("File " + name + " not found.");
		}
		ByteArrayOutputStream out = fakeFilesystem.get(name);
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public OutputStream getOutputStream(String name)
			throws FileNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		fakeFilesystem.put(name, out);
		return out;
	}
}
