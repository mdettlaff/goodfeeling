package goodfeeling.common;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TableTest {

	@Test
	public void testTableCreationVarargs() {
		assertSimpleTableIsAsExpected(getSimpleTable());
	}

	private static Table getSimpleTable() {
		Table table = new Table("foo", "bar");
		table.addRow("a", "b");
		table.addRow("c", "d");
		return table;
	}

	@Test
	public void testTableCreationArrays() {
		String[] columnNames = {"foo", "bar"};
		Object[] row1 = {"a", "b"};
		Object[] row2 = {"c", "d"};
		Table table = new Table(columnNames);
		table.addRow(row1);
		table.addRow(row2);
		assertSimpleTableIsAsExpected(table);
	}

	@Test
	public void testTableCreationLists() {
		List<String> columnNames = Arrays.asList("foo", "bar");
		List<?> row1 = Arrays.asList("a", "b");
		List<?> row2 = Arrays.asList("c", "d");
		Table table = new Table(columnNames);
		table.addRow(row1);
		table.addRow(row2);
		assertSimpleTableIsAsExpected(table);
	}

	@Test
	public void testTableEquality() {
		assertEquals(getSimpleTable(), getSimpleTable());
	}

	@Test
	public void testTableToString() {
		final String actual = getSimpleTable().toString();
		final String expected =
			"[foo, bar]\n" +
			"[a, b]\n" +
			"[c, d]";
		assertEquals(expected, actual);
	}

	private static void assertSimpleTableIsAsExpected(Table table) {
		assertEquals(2, table.getRowCount());
		assertEquals(2, table.getColumnCount());
		assertEquals(Arrays.asList("foo", "bar"), table.getColumnNames());
		assertEquals("a", table.getValueAt(0, 0));
		assertEquals("b", table.getValueAt(0, 1));
		assertEquals("c", table.getValueAt(1, 0));
		assertEquals("d", table.getValueAt(1, 1));
	}
}
