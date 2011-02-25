package goodfeeling.weka;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class RuleTest {

	@Test
	public void testSortRulesByConfidence() {
		List<Rule> rules = new ArrayList<Rule>();
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("foo", "bar");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("baz", "qux");
			Rule rule = new Rule(antecedent, consequent, 0.3);
			rules.add(rule);
		}
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("pij", "mleko");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("będziesz", "wielki");
			Rule rule = new Rule(antecedent, consequent, 0.8);
			rules.add(rule);
		}
		List<Rule> sortedRules = new ArrayList<Rule>(rules);
		Collections.sort(sortedRules, Collections.reverseOrder());
		final List<Rule> expected = Arrays.asList(rules.get(1), rules.get(0));
		assertEquals(expected, sortedRules);
	}
}
