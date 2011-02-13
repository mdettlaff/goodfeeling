package goodfeeling.weka;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class DecisionTreeRulesFinderTest {

	@Test
	public void testParsePriority() {
		{
			double priority =
				DecisionTreeRulesFinder.parsePriority("foo (4.5/2.0)");
			assertEquals(4.5, priority, 0.001);
		}
		{
			double priority =
				DecisionTreeRulesFinder.parsePriority("foo (4.5)");
			assertEquals(4.5, priority, 0.001);
		}
		{
			double priority =
				DecisionTreeRulesFinder.parsePriority("foo (4)");
			assertEquals(4, priority, 0.001);
		}
		{
			double priority =
				DecisionTreeRulesFinder.parsePriority("foo (3) (4)");
			assertEquals(4, priority, 0.001);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParsePriorityException() {
		double priority =
			DecisionTreeRulesFinder.parsePriority("foo");
		assertEquals(0, priority, 0.001);
	}

	@Test
	public void testGetRulesFromDecisionTreeWithOneRule() {
		DecisionTreeRulesFinder finder = new DecisionTreeRulesFinder();
		Tree decisionTree = new Tree("occupation");
		Tree income = new Tree(">50K (7.0)");
		decisionTree.addChild(income, "programmer");
		List<Rule> actual = finder.getRulesFromDecisionTree(decisionTree, "income");

		RulePredicate antecedent = new RulePredicate();
		antecedent.putAttribute("occupation", "programmer");
		RulePredicate consequent = new RulePredicate();
		consequent.putAttribute("income", ">50K");
		Rule rule = new Rule(antecedent, consequent, 1);
		List<Rule> expected = Arrays.asList(rule);

		assertEquals(expected, actual);
	}

	@Test
	public void testFindRulesForSimpleData() throws Exception {
		DecisionTreeRulesFinder rulesFinder = new DecisionTreeRulesFinder();
		List<Rule> actual = rulesFinder.findRules(getTestData());
		List<Rule> expected = getExpectedRules();
		assertEquals(expected, actual);
	}

	static List<Rule> getExpectedRules() {
		List<Rule> rules = new ArrayList<Rule>();
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("race", "= white");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("income", ">50K");
			Rule rule = new Rule(antecedent, consequent, 1);
			rules.add(rule);
		}
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("race", "= black");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("income", "<50K");
			Rule rule = new Rule(antecedent, consequent, 1);
			rules.add(rule);
		}
		return rules;
	}

	private Instances getTestData() {
		FastVector occupations = new FastVector();
		occupations.addElement("programmer");
		occupations.addElement("accountant");
		occupations.addElement("doctor");
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
		{
			Instance instance = new Instance(attrInfo.size());
			instance.setValue(occupation, "doctor");
			instance.setValue(race, "black");
			instance.setValue(income, "<50K");
			data.add(instance);
		}
		data.setClass(income);
		return data;
	}
}
