package goodfeeling.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import goodfeeling.weka.Rule;
import goodfeeling.weka.RulePredicate;
import goodfeeling.weka.RulesFinderTest;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class RuleTranslatorTest {

	@Test
	public void testFindHumanReadableRulesInSampleXMLFileUsingDecisionTree()
	throws Exception {
		List<Rule> rules = RulesFinderTest.findRulesInSampleXMLFileUsingDecisionTree();

		Iterator<Rule> rulesIter = rules.iterator();
		assertTrue("Not enough rules found.", rules.size() > 5);
		{
			final String actualRule = RuleTranslator.humanReadable(rulesIter.next());
			final String expectedRule =
				"It seems likely that your physicalrate is low (2.07/1.0) " +
				"when your food name is Hot dog.";
			assertEquals(expectedRule, actualRule);
		}
		{
			final String actualRule = RuleTranslator.humanReadable(rulesIter.next());
			final String expectedRule =
				"It seems likely that your physicalrate is medium (3.1/1.1) " +
				"when your food name is Fish meat.";
			assertEquals(expectedRule, actualRule);
		}
	}

	@Test
	public void testHumanReadableRule() {
		RulePredicate antecedent = new RulePredicate();
		antecedent.putAttribute("activity intensity", "= medium");
		antecedent.putAttribute("food eaten", "= apple");
		RulePredicate consequent = new RulePredicate();
		consequent.putAttribute("mood", "= good");
		Rule rule = new Rule(antecedent, consequent, 0.9);

		final String expected =
			"It seems likely that your mood is good when your activity " +
			"intensity is medium and your food eaten is apple.";
		final String actual = RuleTranslator.humanReadable(rule);
		assertEquals(expected, actual);
	}
}
