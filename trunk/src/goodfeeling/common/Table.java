package goodfeeling.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A generic table with named columns.
 */
public class Table {

	private final List<String> columnNames;
	private List<List<Object>> rows;

	public Table(String... columnNames) {
		this.columnNames = Arrays.asList(columnNames);
		rows = new ArrayList<List<Object>>();
	}

	public void addRow(Object... row) {
		rows.add(Arrays.asList(row));
	}

	public List<String> getColumnNames() {
		return Collections.unmodifiableList(columnNames);
	}

	public int getColumnCount() {
		return getColumnNames().size();
	}

	public int getRowCount() {
		return rows.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex).get(columnIndex);
	}
}
