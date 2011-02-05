package goodfeeling.db;

import goodfeeling.db.InputOutput;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import org.junit.Test;

public class InMemoryIOTest {

	@Test
	public void testSimpleWriteRead() throws IOException {
		final String expected = "the quick brown fox";
		InputOutput io = new InMemoryIO();
		OutputStream out = io.getOutputStream("foo");
		Writer writer = new PrintWriter(out);
		writer.write(expected);
		writer.close();
		InputStream in = io.getInputStream("foo");
		Reader reader = new InputStreamReader(in);
		String actual = readAsString(reader);
		assertEquals(expected, actual);
	}

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFound() throws FileNotFoundException {
		InputOutput io = new InMemoryIO();
		io.getInputStream("foo");
	}

    private static String readAsString(Reader reader) throws IOException {
    	final int BUFFER_SIZE = 1024;
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE];
        int numRead = 0;
        while ((numRead = reader.read(buffer)) != -1) {
            sb.append(buffer, 0, numRead);
        }
        reader.close();
        return sb.toString();
    }
}
