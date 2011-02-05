package goodfeeling.db;

import static org.junit.Assert.assertEquals;


import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class DbHandlerTest {

	private InMemoryIO io;
	private Calendar today;

	@Before
	public void setUp() {
		io = new InMemoryIO();
		today = Calendar.getInstance();
	}

	@Test
	public void testUserStatePersistence() throws Exception {
		persistUserState();
		assertUserStatePersisted();
	}

	private void persistUserState() throws Exception {
		DbHandler dbHandler = new DbHandler(io);
		Record record = dbHandler.getRecord(today);
		record.moodRates.add(new TestResult("65%"));
		record.mentalRates.add(new TestResult("72%"));
		dbHandler.addOrUpdateRecord(record);
	}

	private void assertUserStatePersisted() {
		DbHandler dbHandler = new DbHandler(io);
		Record record = dbHandler.getRecord(today);
		assertEquals("65%", record.getLastMoodRate());
		assertEquals("72%", record.getLastMentalRate());
	}
}
