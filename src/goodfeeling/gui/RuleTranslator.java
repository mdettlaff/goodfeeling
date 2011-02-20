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
		message.append(humanReadable(consequent, " is "));
		message.append("when ");
		RulePredicate antecedent = rule.getAntecedent();
		message.append(humanReadable(antecedent, " "));
		message.setCharAt(message.length() - 1, '.');
		return message.toString();
	}

	private static String humanReadable(RulePredicate predicate, String relation) {
		StringBuilder message = new StringBuilder();
		for (Map.Entry<String, String> entry : predicate.getAttributeMappings()) {
			String attribute = entry.getKey();
			String attributeValue = entry.getValue();
			message.append("your ");
			message.append(humanReadableAttributeName(attribute));
			message.append(relation);
			message.append(attributeValue);
			message.append(" and ");
		}
		String postprocessedMessage = message.toString();
		postprocessedMessage = postprocessMessage(postprocessedMessage);
		return postprocessedMessage;
	}

	private static String postprocessMessage(final String in) {
		String out = in;
		out = out.replaceFirst("and $", "");
		out = out.replace(" = ", " is ");
		out = out.replaceAll("'\\(-inf.*?'", "low");
		out = out.replaceAll("'.*?inf\\)'", "high");
		out = out.replaceAll("'\\(\\d.*?'", "medium");
		return out;
	}

	private static String humanReadableConfidence(double confidence) {
		if (confidence >= 0.6) {
			return "It seems likely that ";
		} else {
			return "It might be that ";
		}
	}

	private static String humanReadableAttributeName(String attributeName) {
		return attributeName.replaceAll("\\d", " ");
	}
}
