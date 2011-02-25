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

public class AssociatorRulesFinderTest {

	@Test
	public void testFindRulesForSimpleData() throws Exception {
		AssociatorRulesFinder rulesFinder = new AssociatorRulesFinder();
		List<Rule> rules = rulesFinder.findRules(getTestData());
		List<Rule> expected = getExpectedRules();
		assertTrue("Not enough rules found.", rules.size() > expected.size());
		List<Rule> actual = rules.subList(0, expected.size());
		assertEquals(expected, actual);
	}

	static List<Rule> getExpectedRules() {
		RulePredicate highIncome = new RulePredicate();
		highIncome.putAttribute("income", ">50K");
		RulePredicate whiteRace = new RulePredicate();
		whiteRace.putAttribute("race", "white");
		RulePredicate programmerOccupation = new RulePredicate();
		programmerOccupation.putAttribute("occupation", "programmer");
		List<Rule> expected = Arrays.asList(
				new Rule(highIncome, whiteRace, 1),
				new Rule(whiteRace, highIncome, 1),
				new Rule(programmerOccupation, whiteRace, 1)
		);
		return expected;
	}

	static Instances getTestData() {
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
		Instances data = new Instances("test", attrInfo, attrInfo.size());
		{
			Instance instance = new Instance(attrInfo.size());
			instance.setValue(occupation, "programmer");
			instance.setValue(race, "white");
			instance.setValue(income, ">50K");
			data.add(instance);
		}
		{
			Instance instance = new Instance(attrInfo.size());
			instance.setValue(occupation, "accountant");
			instance.setValue(race, "white");
			instance.setValue(income, ">50K");
			data.add(instance);
		}
		{
			Instance instance = new Instance(attrInfo.size());
			instance.setValue(occupation, "accountant");
			instance.setValue(race, "black");
			instance.setValue(income, "<50K");
			data.add(instance);
		}
		data.setClass(income);
		return data;
	}
}
