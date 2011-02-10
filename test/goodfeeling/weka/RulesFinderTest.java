package goodfeeling.weka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import goodfeeling.common.Table;

public class RulesFinderTest {

	@Test
	public void testFindRulesUsingAssociator() {
		Table data = new Table("occupation", "race", "income");
		data.addRow("programmer", "white", ">50K");
		data.addRow("accountant", "white", ">50K");
		data.addRow("accountant", "black", "<50K");

		RulesFinder rulesFinder = new RulesFinder(data);
		rulesFinder.setConcreteRulesFinder(new AssociatorRulesFinder());
		List<Rule> rules = rulesFinder.findRules();

		List<Rule> expected = AssociatorRulesFinderTest.getExpectedRules();
		assertTrue("Not enough rules found.", rules.size() > expected.size());
		List<Rule> actual = rules.subList(0, expected.size());
		assertEquals(expected, actual);
	}

	@Test
	public void testFindRulesUsingDecisionTree() {
		Table data = new Table("occupation", "race", "income");
		data.addRow("programmer", "white", ">50K");
		data.addRow("accountant", "white", ">50K");
		data.addRow("accountant", "black", "<50K");
		data.addRow("doctor", "black", "<50K");

		RulesFinder rulesFinder = new RulesFinder(data);
		rulesFinder.setConcreteRulesFinder(new DecisionTreeRulesFinder());
		List<Rule> actual = rulesFinder.findRules();

		List<Rule> expected = DecisionTreeRulesFinderTest.getExpectedRules();
		assertEquals(expected, actual);
	}
}
