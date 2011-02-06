package goodfeeling.weka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import goodfeeling.common.Table;

public class RulesFinderTest {

	@Test
	public void testFindRules() {
		Table data = new Table("occupation", "race", "income");
		data.addRow("programmer", "white", ">50K");
		data.addRow("accountant", "white", ">50K");
		data.addRow("accountant", "black", "<50K");

		RulesFinder rulesFinder = new RulesFinder(data);
		List<Rule> rules = rulesFinder.findRules();

		List<Rule> expected = InstancesRulesFinderTest.getExpectedRules();
		assertTrue("Not enough rules found.", rules.size() > expected.size());
		List<Rule> actual = rules.subList(0, expected.size());
		assertEquals(expected, actual);
	}
}
