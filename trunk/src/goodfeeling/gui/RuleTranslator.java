package goodfeeling.gui;

import goodfeeling.weka.Rule;
import goodfeeling.weka.RulePredicate;

import java.util.Map;

public class RuleTranslator {

	/**
	 * Translates a rule to a human readable format.
	 */
	public static String humanReadable(Rule rule) {
		StringBuilder message = new StringBuilder();
		message.append(humanReadableConfidence(rule.getConfidence()));
		RulePredicate consequent = rule.getConsequent();
		message.append(humanReadable(consequent));
		message.append("when ");
		RulePredicate antecedent = rule.getAntecedent();
		message.append(humanReadable(antecedent));
		message.setCharAt(message.length() - 1, '.');
		return message.toString();
	}

	private static String humanReadable(RulePredicate predicate) {
		StringBuilder message = new StringBuilder();
		for (Map.Entry<String, String> entry : predicate.getAttributeMappings()) {
			String attribute = entry.getKey();
			String attributeValue = entry.getValue();
			message.append("your ");
			message.append(attribute);
			message.append(" is ");
			message.append(attributeValue);
			message.append(" and ");
		}
		return message.toString().replaceFirst("and $", "");
	}

	private static String humanReadableConfidence(double confidence) {
		if (confidence >= 0.8) {
			return "It seems likely that ";
		} else {
			return "It might be that ";
		}
	}
}
