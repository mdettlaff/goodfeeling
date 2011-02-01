package goodfeeling.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;

import goodfeeling.db.FileIO;

public class AndroidFileIO implements FileIO {

	private final Context context;

	public AndroidFileIO(Context context) {
		this.context = context;
	}

	@Override
	public FileInputStream getFileInputStream(String name)
			throws FileNotFoundException {
		return context.openFileInput(name);
	}

	@Override
	public FileOutputStream getFileOutputStream(String name)
			throws FileNotFoundException {
		return context.openFileOutput(name, Context.MODE_PRIVATE);
	}

}
