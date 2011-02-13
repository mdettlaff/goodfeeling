package goodfeeling.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

	private IOUtils() {
	}

	/**
	 * Writes all data from input stream to output stream.
	 */
	public static void pipe(InputStream in, OutputStream out)
	throws IOException {
		final int BUFFER_SIZE = 1024;
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesReadCount;
		while ((bytesReadCount = in.read(buffer)) >= 0) {
			out.write(buffer, 0, bytesReadCount);
		}
	}
}
