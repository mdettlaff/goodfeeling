package goodfeeling.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import goodfeeling.weka.Rule;
import goodfeeling.weka.RulePredicate;
import goodfeeling.weka.RulesFinderTest;

import java.util.List;

import org.junit.Test;

public class RuleTranslatorTest {

	@Test
	public void testFindHumanReadableRulesInSampleXMLFileUsingDecisionTree()
	throws Exception {
		List<Rule> rules = RulesFinderTest.findRulesInSampleXMLFileUsingDecisionTree();

		assertTrue("Not enough rules found.", rules.size() > 5);
		final String actualRule1 = RuleTranslator.humanReadable(rules.get(0));
		final String expectedRule1 =
			"It seems likely that your physicalrate is suberb (2.63/0.63) " +
			"when your food is = Watermelon and your activity is = High.";
		assertEquals(expectedRule1, actualRule1);
	}

	@Test
	public void testHumanReadableRule() {
		RulePredicate antecedent = new RulePredicate();
		antecedent.putAttribute("activity intensity", "medium");
		antecedent.putAttribute("food eaten", "apple");
		RulePredicate consequent = new RulePredicate();
		consequent.putAttribute("mood", "good");
		Rule rule = new Rule(antecedent, consequent, 0.9);

		final String expected =
			"It seems likely that your mood is good when your activity " +
			"intensity is medium and your food eaten is apple.";
		final String actual = RuleTranslator.humanReadable(rule);
		assertEquals(expected, actual);
	}
}
