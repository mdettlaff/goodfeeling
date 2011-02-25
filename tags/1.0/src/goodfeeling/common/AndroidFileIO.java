package goodfeeling.common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

import goodfeeling.db.InputOutput;

public class AndroidFileIO implements InputOutput {

	private final Context context;

	public AndroidFileIO(Context context) {
		this.context = context;
	}

	@Override
	public InputStream getInputStream(String name) throws FileNotFoundException {
		return context.openFileInput(name);
	}

	@Override
	public OutputStream getOutputStream(String name)
			throws FileNotFoundException {
		return context.openFileOutput(name, Context.MODE_PRIVATE);
	}

}
