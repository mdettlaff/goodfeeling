package goodfeeling.weka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class RulesFinderTest {

	@Test
	public void testFindRulesForSimpleData() throws Exception {
		RulesFinder rulesFinder = new RulesFinder(getTestData());
		List<Rule> rules = rulesFinder.findRules();
		RulePredicate highIncome = new RulePredicate();
		highIncome.putAttribute("income", ">50K");
		RulePredicate whiteRace = new RulePredicate();
		whiteRace.putAttribute("race", "white");
		RulePredicate programmerOccupation = new RulePredicate();
		programmerOccupation.putAttribute("occupation", "programmer");
		List<Rule> expected = Arrays.asList(
				new Rule(highIncome, whiteRace),
				new Rule(whiteRace, highIncome),
				new Rule(programmerOccupation, whiteRace)
		);
		assertTrue("Not enough rules found.", rules.size() > expected.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), rules.get(i));
		}
	}

	private Instances getTestData() {
		final int attributesCount = 3;
		FastVector occupations = new FastVector();
		occupations.addElement("programmer");
		occupations.addElement("accountant");
		Attribute occupation = new Attribute("occupation", occupations);
		FastVector races = new FastVector();
		races.addElement("white");
		races.addElement("black");
		Attribute race = new Attribute("race", races);
		FastVector incomes = new FastVector();
		incomes.addElement(">50K");
		incomes.addElement("<50K");
		Attribute income = new Attribute("income", incomes);

		FastVector attrInfo = new FastVector();
		attrInfo.addElement(occupation);
		attrInfo.addElement(race);
		attrInfo.addElement(income);
		Instances data = new Instances("test", attrInfo , attributesCount);

		Instance instance1 = new Instance(attributesCount);
		instance1.setValue(occupation, "programmer");
		instance1.setValue(race, "white");
		instance1.setValue(income, ">50K");
		data.add(instance1);

		Instance instance2 = new Instance(attributesCount);
		instance2.setValue(occupation, "accountant");
		instance2.setValue(race, "white");
		instance2.setValue(income, ">50K");
		data.add(instance2);

		Instance instance3 = new Instance(attributesCount);
		instance3.setValue(occupation, "accountant");
		instance3.setValue(race, "black");
		instance3.setValue(income, "<50K");
		data.add(instance3);

		data.setClass(income);
		return data;
	}
}
