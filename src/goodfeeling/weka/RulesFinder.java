package goodfeeling.weka;

import goodfeeling.common.Table;

import java.util.List;

import weka.core.Instances;

public class RulesFinder {

	private final Table data;
	private final String classColumnName;
	private final IRulesFinder defaultInternalRulesFinder;
	private IRulesFinder internalRulesFinder;

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
		defaultInternalRulesFinder = new AssociatorRulesFinder();
	}

	void setConcreteRulesFinder(IRulesFinder rulesFinder) {
		this.internalRulesFinder = rulesFinder;
	}

	public List<Rule> findRules() {
		try {
			TableToInstancesConverter converter =
				new TableToInstancesConverter(data, classColumnName);
			Instances data = converter.convert();
			IRulesFinder finder = getConcreteRulesFinder();
			return finder.findRules(data);
		} catch (Exception e) {
			throw new RuntimeException("Finding rules using Weka failed.", e);
		}
	}

	private IRulesFinder getConcreteRulesFinder() {
		if (internalRulesFinder != null) {
			return internalRulesFinder;
		} else {
			return defaultInternalRulesFinder;
		}
	}
}
