package goodfeeling.weka;

import static org.junit.Assert.assertEquals;

import goodfeeling.common.Table;

import java.util.Enumeration;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class TableToInstancesConverterTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testConvert() {
		Table table = new Table("occupation", "race", "income");
		table.addRow("programmer", "white", ">50K");
		table.addRow("accountant", "white", ">50K");
		table.addRow("accountant", "black", "<50K");

		TableToInstancesConverter converter =
			new TableToInstancesConverter(table, "income");
		Instances data = converter.convert();

		Enumeration<Attribute> attributes = data.enumerateAttributes();

		Attribute occupation = attributes.nextElement();
		assertEquals("occupation", occupation.name());
		Enumeration<String> occupations = occupation.enumerateValues();
		assertEquals("programmer", occupations.nextElement());
		assertEquals("accountant", occupations.nextElement());

		Attribute race = attributes.nextElement();
		assertEquals("race", race.name());
		Enumeration<String> races = race.enumerateValues();
		assertEquals("white", races.nextElement());
		assertEquals("black", races.nextElement());

		Attribute income = data.classAttribute();
		assertEquals("income", income.name());
		Enumeration<String> incomes = income.enumerateValues();
		assertEquals(">50K", incomes.nextElement());
		assertEquals("<50K", incomes.nextElement());

		Enumeration<Instance> instances = data.enumerateInstances();
		Instance row1 = instances.nextElement();
		assertEquals("programmer", row1.stringValue(occupation));
		assertEquals("white", row1.stringValue(race));
		assertEquals(">50K", row1.stringValue(income));
		Instance row2 = instances.nextElement();
		assertEquals("accountant", row2.stringValue(occupation));
		assertEquals("white", row2.stringValue(race));
		assertEquals(">50K", row2.stringValue(income));
		Instance row3 = instances.nextElement();
		assertEquals("accountant", row3.stringValue(occupation));
		assertEquals("black", row3.stringValue(race));
		assertEquals("<50K", row3.stringValue(income));
	}
}
