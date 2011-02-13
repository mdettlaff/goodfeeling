package goodfeeling.weka;

import goodfeeling.common.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;

public class RulesFinder {

	private static final int DEFAULT_DISCRETIZE_BINS_COUNT = 3;

	private static final double MIN_CONFIDENCE = 0.4;

	private final Table dataTable;
	private final int classColumnIndex;
	private IRulesFinder internalRulesFinder;
	private int discretizeBinsCount = DEFAULT_DISCRETIZE_BINS_COUNT;

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
		this.dataTable = data;
		this.classColumnIndex = dataTable.getColumnNames().indexOf(classColumnName);
		IRulesFinder defaultInternalRulesFinder = new DecisionTreeRulesFinder();
		internalRulesFinder = defaultInternalRulesFinder;
	}

	public List<Rule> findRules() {
		try {
			TableToInstancesConverter converter =
				new TableToInstancesConverter(dataTable);
			Instances instances = converter.convert();
			instances = discretize(instances);
			instances.setClassIndex(classColumnIndex);
			List<Rule> rules = internalRulesFinder.findRules(instances);
			rules = filterRulesWithTooSmallConfidence(rules);
			sortRulesByDescendingConfidence(rules);
			return rules;
		} catch (Exception e) {
			throw new RuntimeException("Finding rules using Weka failed.", e);
		}
	}

	void setConcreteRulesFinder(IRulesFinder rulesFinder) {
		this.internalRulesFinder = rulesFinder;
	}

	void setDiscretizeBinsCount(int discretizeBinsCount) {
		this.discretizeBinsCount = discretizeBinsCount;
	}

	private void sortRulesByDescendingConfidence(List<Rule> rules) {
		Collections.sort(rules, Collections.reverseOrder());
	}

	private List<Rule> filterRulesWithTooSmallConfidence(List<Rule> rules) {
		List<Rule> filteredRules = new ArrayList<Rule>();
		for (Rule rule : rules) {
			if (rule.getConfidence() >= MIN_CONFIDENCE) {
				filteredRules.add(rule);
			}
		}
		return filteredRules;
	}

	private Instances discretize(Instances data) throws Exception {
		Discretize discretize = new Discretize();
		discretize.setBins(discretizeBinsCount);
		data = WekaUtils.applyFilter(data, discretize);
		return data;
	}
}
