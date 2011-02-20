package goodfeeling.weka;

import goodfeeling.common.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;

/**
 * Entry point of the Weka integration layer.
 */
public class RulesFinder {

	private static final int DEFAULT_DISCRETIZE_BINS_COUNT = 3;

	private static final double MIN_CONFIDENCE = 0.4;

	private final Table dataTable;
	private final int classColumnIndex;
	private IRulesFinder internalRulesFinder;
	private int discretizeBinsCount = DEFAULT_DISCRETIZE_BINS_COUNT;

	/**
	 * @param data Data table. The last column is used as a class attribute.
	 */
	public RulesFinder(Table data) {
		this(data, data.getColumnNames().get(data.getColumnCount() - 1));
	}

	/**
	 * @param data            Data table.
	 * @param classColumnName Name of the column in the data table
	 *                        to be used as a class attribute.
	 */
	public RulesFinder(Table data, String classColumnName) {
		this.dataTable = data;
		this.classColumnIndex = dataTable.getColumnNames().indexOf(classColumnName);
		IRulesFinder defaultInternalRulesFinder = new DecisionTreeRulesFinder();
		internalRulesFinder = defaultInternalRulesFinder;
	}

	/**
	 * Searches for regularities in the data table, and returns them
	 * in the form of rules. J48 algorithm is used by default.
	 */
	public List<Rule> findRules() {
		try {
			Instances instances = preprocessData();
			List<Rule> rules;
			if (isDataSufficient(instances)) {
				rules = internalRulesFinder.findRules(instances);
				rules = filterRulesWithTooSmallConfidence(rules);
				sortRulesByDescendingConfidence(rules);
			} else {
				rules = new ArrayList<Rule>();
			}
			return rules;
		} catch (Exception e) {
			throw new RuntimeException("Finding rules using Weka failed.", e);
		}
	}

	private Instances preprocessData() throws Exception {
		TableToInstancesConverter converter =
			new TableToInstancesConverter(dataTable);
		Instances instances = converter.convert();
		instances = discretize(instances);
		instances.setClassIndex(classColumnIndex);
		return instances;
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

	private boolean isDataSufficient(Instances instances) {
		return instances.numInstances() > 0 && !isUnaryClass(instances);
	}

	private boolean isUnaryClass(Instances instances) {
		return instances.numDistinctValues(instances.classAttribute()) == 1;
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
