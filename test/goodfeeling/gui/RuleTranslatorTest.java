package goodfeeling.gui;

import static org.junit.Assert.assertEquals;
import goodfeeling.weka.Rule;
import goodfeeling.weka.RulePredicate;

import org.junit.Test;

public class RuleTranslatorTest {

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
