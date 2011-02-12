package goodfeeling.weka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import goodfeeling.common.Table;
import goodfeeling.db.DbHandler;
import goodfeeling.db.InMemoryIO;
import goodfeeling.db.InputOutput;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RulesFinderTest {

	@Test
	public void testFindRulesInSampleXMLFileUsingDecisionTree()
	throws Exception {
		List<Rule> rules = findRulesInSampleXMLFileUsingDecisionTree();

		assertTrue("Not enough rules found.", rules.size() > 5);
		final String actualRule1 = rules.get(0).toString();
		final String expectedRule1 =
			"food2name== Watermelon activity0intensivity== High ==> " +
			"physicalrate=suberb (2.63/0.63)";
		assertEquals(expectedRule1, actualRule1);
	}

	public static List<Rule> findRulesInSampleXMLFileUsingDecisionTree()
			throws FileNotFoundException, IOException, Exception {
		DbHandler dbHandler = getDbHandlerWithSampleXMLData();

		Table data = dbHandler.generateDataTable();

		RulesFinder rulesFinder = new RulesFinder(data);
		rulesFinder.setConcreteRulesFinder(new DecisionTreeRulesFinder());
		List<Rule> rules = rulesFinder.findRules();
		return rules;
	}

	private static DbHandler getDbHandlerWithSampleXMLData()
			throws FileNotFoundException, IOException {
		InputOutput io = new InMemoryIO();
		String xmlFilename = "2010_2.xml";
		InputStream in = RulesFinderTest.class.getResourceAsStream(xmlFilename);
		OutputStream out = io.getOutputStream(xmlFilename);
		flow(in, out);
		DbHandler dbHandler = new DbHandler(io);
		return dbHandler;
	}

	private static void flow(InputStream in, OutputStream out)
	throws IOException {
		final int BUFFER_SIZE = 1024;
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesReadCount;
		while ((bytesReadCount = in.read(buffer)) >= 0) {
			out.write(buffer, 0, bytesReadCount);
		}
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

	@Test
	public void testFindRulesUsingDecisionTreeWithMissingValues() {
		Table data = new Table("occupation", "race", "income");
		data.addRow("programmer", "white", ">50K");
		data.addRow(null, "white", ">50K");
		data.addRow("accountant", "black", "<50K");
		data.addRow(null, "black", "<50K");

		RulesFinder rulesFinder = new RulesFinder(data);
		rulesFinder.setConcreteRulesFinder(new DecisionTreeRulesFinder());
		List<Rule> actual = rulesFinder.findRules();

		List<Rule> expected = DecisionTreeRulesFinderTest.getExpectedRules();
		assertEquals(expected, actual);
	}

	@Test
	public void testFindRulesUsingDecisionTreeWithNumericAttributes() {
		Table data = new Table("occupation", "race", "income");
		data.addRow("programmer", 1, ">50K");
		data.addRow("accountant", 1, ">50K");
		data.addRow("accountant", 2, "<50K");
		data.addRow("doctor", 2, "<50K");

		RulesFinder rulesFinder = new RulesFinder(data);
		rulesFinder.setConcreteRulesFinder(new DecisionTreeRulesFinder());
		rulesFinder.setDiscretizeBinsCount(2);
		List<Rule> actual = rulesFinder.findRules();

		List<Rule> expected = getExpectedRulesForNumericAttributes();
		assertEquals(expected, actual);
	}

	private static List<Rule> getExpectedRulesForNumericAttributes() {
		List<Rule> rules = new ArrayList<Rule>();
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("race", "= '(-inf-1.5]'");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("income", ">50K (2.0)");
			Rule rule = new Rule(antecedent, consequent, 1);
			rules.add(rule);
		}
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("race", "= '(1.5-inf)'");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("income", "<50K (2.0)");
			Rule rule = new Rule(antecedent, consequent, 1);
			rules.add(rule);
		}
		return rules;
	}

	@Test
	public void testFindRulesUsingDecisionTreeWithNumericClassAttributes() {
		Table data = new Table("occupation", "race", "income");
		data.addRow("programmer", "white", 70.0);
		data.addRow("accountant", "white", 60.0);
		data.addRow("accountant", "black", 30.0);
		data.addRow("doctor", "black", 40.0);

		RulesFinder rulesFinder = new RulesFinder(data);
		rulesFinder.setConcreteRulesFinder(new DecisionTreeRulesFinder());
		rulesFinder.setDiscretizeBinsCount(2);
		List<Rule> actual = rulesFinder.findRules();

		List<Rule> expected = getExpectedRulesForDiscretizedClass();
		assertEquals(expected, actual);
	}

	private static List<Rule> getExpectedRulesForDiscretizedClass() {
		List<Rule> rules = new ArrayList<Rule>();
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("race", "= white");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("income", "'(50-inf)' (2.0)");
			Rule rule = new Rule(antecedent, consequent, 1);
			rules.add(rule);
		}
		{
			RulePredicate antecedent = new RulePredicate();
			antecedent.putAttribute("race", "= black");
			RulePredicate consequent = new RulePredicate();
			consequent.putAttribute("income", "'(-inf-50]' (2.0)");
			Rule rule = new Rule(antecedent, consequent, 1);
			rules.add(rule);
		}
		return rules;
	}

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
}
