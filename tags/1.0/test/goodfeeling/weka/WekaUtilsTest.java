package goodfeeling.weka;

import static org.junit.Assert.assertEquals;
import goodfeeling.common.Table;

import java.util.Enumeration;

import org.junit.Test;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Discretize;

public class WekaUtilsTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testDiscretize() throws Exception {
		Table table = new Table("occupation", "race", "income");
		table.addRow("programmer", 1, ">50K");
		table.addRow("accountant", 1, ">50K");
		table.addRow("accountant", 2, "<50K");

		TableToInstancesConverter converter =
			new TableToInstancesConverter(table);
		Instances data = converter.convert();
		Discretize discretize = new Discretize();
		discretize.setBins(2);
		data = WekaUtils.applyFilter(data, discretize);
		data.setClassIndex(table.getColumnNames().indexOf("income"));

		Enumeration<Attribute> attributes = data.enumerateAttributes();

		Attribute occupation = attributes.nextElement();
		assertEquals("occupation", occupation.name());
		Enumeration<String> occupations = occupation.enumerateValues();
		assertEquals("programmer", occupations.nextElement());
		assertEquals("accountant", occupations.nextElement());

		Attribute race = attributes.nextElement();
		assertEquals("race", race.name());

		Attribute income = data.classAttribute();
		assertEquals("income", income.name());

		Enumeration<Instance> instances = data.enumerateInstances();
		Instance row1 = instances.nextElement();
		assertEquals("programmer", row1.stringValue(occupation));
		assertEquals("'(-inf-1.5]'", row1.stringValue(race));
		assertEquals(">50K", row1.stringValue(income));
		Instance row2 = instances.nextElement();
		assertEquals("accountant", row2.stringValue(occupation));
		assertEquals("'(-inf-1.5]'", row2.stringValue(race));
		assertEquals(">50K", row2.stringValue(income));
		Instance row3 = instances.nextElement();
		assertEquals("accountant", row3.stringValue(occupation));
		assertEquals("'(1.5-inf)'", row3.stringValue(race));
		assertEquals("<50K", row3.stringValue(income));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testDiscretizeClassAttribute() throws Exception {
		Table table = new Table("occupation", "race", "income");
		table.addRow("programmer", "white", 70.0);
		table.addRow("accountant", "black", 30.0);

		TableToInstancesConverter converter =
			new TableToInstancesConverter(table);
		Instances data = converter.convert();
		Discretize discretize = new Discretize();
		discretize.setBins(2);
		data = WekaUtils.applyFilter(data, discretize);

		data.setClassIndex(table.getColumnNames().indexOf("income"));

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

		Enumeration<Instance> instances = data.enumerateInstances();
		Instance row1 = instances.nextElement();
		assertEquals("programmer", row1.stringValue(occupation));
		assertEquals("white", row1.stringValue(race));
		assertEquals("'(50-inf)'", row1.stringValue(income));
		Instance row2 = instances.nextElement();
		assertEquals("accountant", row2.stringValue(occupation));
		assertEquals("black", row2.stringValue(race));
		assertEquals("'(-inf-50]'", row2.stringValue(income));
	}
}
