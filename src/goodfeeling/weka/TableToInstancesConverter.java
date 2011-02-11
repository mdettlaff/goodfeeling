package goodfeeling.weka;

import goodfeeling.common.Table;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

class TableToInstancesConverter {

	private final Table table;
	private final Object classColumnName;

	public TableToInstancesConverter(
			Table table, String classColumnName) {
		this.table = table;
		this.classColumnName = classColumnName;
	}

	public Instances convert() {
		FastVector attrInfo = new FastVector();
		for (int col = 0; col < table.getColumnCount(); col++) {
			FastVector possibleAttributeValues = new FastVector();
			String attributeName = table.getColumnNames().get(col);
			for (int row = 0; row < table.getRowCount(); row++) {
				Object value = table.getValueAt(row, col);
				if (value != null && !(value instanceof Number)
						&& !(value instanceof String)) {
					throw new IllegalArgumentException(
							"Only String and Number instances are allowed.");
				}
				boolean isValueEmpty = isValueEmpty(value);
				if (!isValueEmpty && (value instanceof String)
						&& !possibleAttributeValues.contains(value)) {
					possibleAttributeValues.addElement(value);
				}
			}
			Attribute attribute;
			if (possibleAttributeValues.size() > 0) {
				// nominal attribute
				attribute = new Attribute(attributeName, possibleAttributeValues);
			} else {
				// numeric attribute
				attribute = new Attribute(attributeName);
			}
			attrInfo.addElement(attribute);
		}
		Instances data = new Instances(null, attrInfo, table.getColumnCount());
		for (int row = 0; row < table.getRowCount(); row++) {
			Instance instance = new Instance(attrInfo.size());
			for (int col = 0; col < table.getColumnCount(); col++) {
				Attribute attribute = (Attribute)attrInfo.elementAt(col);
				Object value = table.getValueAt(row, col);
				if (isValueEmpty(value)) {
					instance.setMissing(attribute);
				} else if (value instanceof Number) {
					instance.setValue(attribute, ((Number)value).doubleValue());
				} else {
					instance.setValue(attribute, value.toString());
				}
			}
			data.add(instance);
		}
		data.setClass(getClassAttribute(attrInfo));
		return data;
	}

	private boolean isValueEmpty(Object value) {
		return value == null || value.toString().length() == 0;
	}

	private Attribute getClassAttribute(FastVector attrInfo) {
		int classIndex = table.getColumnNames().indexOf(classColumnName);
		Attribute classAttribute = (Attribute)attrInfo.elementAt(classIndex);
		return classAttribute;
	}
}
