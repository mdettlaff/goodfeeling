package goodfeeling.weka;

import goodfeeling.common.Table;

import java.util.List;

import weka.core.Instances;

public class RulesFinder {

	private final Table data;
	private final String classColumnName;

	/**
	 * The last column in the data table is used as a class attribute.
	 */
	public RulesFinder(Table data) {
		this(data, data.getColumnNames().get(data.getColumnCount() - 1));
	}

	/**
	 * @param classColumnName Name of the column used as a class attribute.
	 */
	public RulesFinder(Table data, String classColumnName) {
		this.data = data;
		this.classColumnName = classColumnName;
	}

	public List<Rule> findRules() {
		try {
			TableToInstancesConverter converter =
				new TableToInstancesConverter(data, classColumnName);
			Instances instances = converter.convert();
			InstancesRulesFinder finder = new InstancesRulesFinder(instances);
			return finder.findRules();
		} catch (Exception e) {
			throw new RuntimeException("Finding rules using Weka failed.", e);
		}
	}
}
