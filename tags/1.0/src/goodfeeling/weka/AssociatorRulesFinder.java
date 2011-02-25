package goodfeeling.weka;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.associations.Apriori;
import weka.associations.Associator;
import weka.core.Instances;

class AssociatorRulesFinder implements IRulesFinder {

	private static final String RULE_REGEX =
		"\\s*\\d+\\. (.+?) \\d+ ==> (.+?) \\d\\s+conf:\\((.+?)\\).*";

	@Override
	public List<Rule> findRules(Instances data) throws Exception {
		Associator associator = new Apriori();
		associator.buildAssociations(data);
		String rawRules = associator.toString();
		return parseAssociatorOutput(rawRules);
	}

	private List<Rule> parseAssociatorOutput(String rawRules) {
		List<Rule> rules = new ArrayList<Rule>();
		for (String line : rawRules.split("\n")) {
			if (line.matches(RULE_REGEX)) {
				rules.add(parseRule(line));
			}
		}
		return rules;
	}

	private Rule parseRule(String line) {
		Pattern pattern = Pattern.compile(RULE_REGEX);
		Matcher matcher = pattern.matcher(line);
		if (!matcher.find()) {
			throw new IllegalArgumentException(
					"A rule was not found in line: " + line);
		}
		RulePredicate antecedent = parsePredicate(matcher.group(1));
		RulePredicate consequent = parsePredicate(matcher.group(2));
		double conficence = Double.parseDouble(matcher.group(3));
		return new Rule(antecedent, consequent, conficence);
	}

	private RulePredicate parsePredicate(String rawPredicate) {
		if (!(rawPredicate + " ").matches("(.+?=.+? )+")) {
			throw new IllegalArgumentException(
					"Malformed predicate: " + rawPredicate);
		}
		RulePredicate predicate = new RulePredicate();
		for (String attributeWithValue : rawPredicate.split(" ")) {
			String[] attributeAndValue = attributeWithValue.split("=");
			String attributeName = attributeAndValue[0];
			String attributeValue = attributeAndValue[1];
			predicate.putAttribute(attributeName, attributeValue);
		}
		return predicate;
	}
}
