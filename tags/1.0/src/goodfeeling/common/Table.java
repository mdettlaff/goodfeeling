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

	public Table(List<String> columnNames) {
		this.columnNames = new ArrayList<String>(columnNames);
		rows = new ArrayList<List<Object>>();
	}

	public Table(String... columnNames) {
		this(Arrays.asList(columnNames));
	}

	public void addRow(List<?> row) {
		if (columnNames.size() != row.size()) {
			throw new IllegalArgumentException("Cannot add a row with " +
					row.size() + " elements to a table with " +
					columnNames.size() + " columns.");
		}
		rows.add(new ArrayList<Object>(row));
	}

	public void addRow(Object... row) {
		addRow(Arrays.asList(row));
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

	public Table truncated(int size) {
		Table truncated = new Table(columnNames);
		for (int i = 0; i < size && i < getRowCount(); i++) {
			truncated.addRow(rows.get(i));
		}
		return truncated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnNames == null) ? 0 : columnNames.hashCode());
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (columnNames == null) {
			if (other.columnNames != null)
				return false;
		} else if (!columnNames.equals(other.columnNames))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(columnNames + "\n");
		for (List<Object> row : rows) {
			sb.append(row + "\n");
		}
		return sb.toString().trim();
	}
}
