package goodfeeling.weka;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Drawable;
import weka.core.Instances;

class DecisionTreeRulesFinder implements IRulesFinder {

	private static final String DOUBLE_REGEX = "\\d+(.\\d+)?";
	private static final String PRIORITY_REGEX =
		"\\s+\\((" + DOUBLE_REGEX + ")(/" + DOUBLE_REGEX + ")?\\)\\s*$";

	@Override
	public List<Rule> findRules(Instances data) throws Exception {
		Classifier classifier = new J48();
		classifier.buildClassifier(data);

		if (classifier instanceof Drawable) {
			Drawable graph = (Drawable)classifier;
			if (graph.graphType() != Drawable.TREE) {
				throw new RuntimeException("Classifier graph is not a decision tree.");
			}
			String rawDecisionTree = graph.graph();
			Tree decisionTree = Tree.parseDOT(rawDecisionTree);
			String classAttributeName = data.classAttribute().name();
			return getRulesFromDecisionTree(decisionTree, classAttributeName);
		} else {
			throw new RuntimeException("Classifier must be an instance of Drawable.");
		}
	}

	List<Rule> getRulesFromDecisionTree(
			Tree decisionTree, String classAttributeName) {
		List<Rule> rules = new ArrayList<Rule>();
		List<List<Tree>> paths = decisionTree.getAllPathsFromRootToLeaf();
		double maxPriority = getMaxPriority(paths);
		for (List<Tree> path : paths) {
			if (path.size() > 1) {
				RulePredicate antecedent = new RulePredicate();
				for (int i = 0; i < path.size() - 1; i++) {
					Tree currentTree = path.get(i);
					Tree nextTree = path.get(i + 1);
					String attributeName = currentTree.getLabel();
					String attributeValue = currentTree.getEdgeLabel(nextTree);
					antecedent.putAttribute(attributeName, attributeValue);
				}
				RulePredicate consequent = new RulePredicate();
				String attributeValue = path.get(path.size() - 1).getLabel();
				double priority = parsePriority(attributeValue);
				attributeValue = attributeValue.replaceFirst(PRIORITY_REGEX, "");
				consequent.putAttribute(classAttributeName, attributeValue);
				double confidence = computeConfidence(priority, maxPriority);
				Rule rule = new Rule(antecedent, consequent, confidence);
				rules.add(rule);
			}
		}
		return rules;
	}

	private double computeConfidence(double priority, double maxPriority) {
		return maxPriority == 0 ? 0 : priority / maxPriority;
	}

	private double getMaxPriority(List<List<Tree>> paths) {
		double maxPriority = 0;
		for (List<Tree> path : paths) {
			if (path.size() > 1) {
				String attributeValue = path.get(path.size() - 1).getLabel();
				double priority = parsePriority(attributeValue);
				if (priority > maxPriority) {
					maxPriority = priority;
				}
			}
		}
		return maxPriority;
	}

	static double parsePriority(String attributeValue) {
		Pattern pattern = Pattern.compile(PRIORITY_REGEX);
		Matcher matcher = pattern.matcher(attributeValue);
		if (matcher.find()) {
			return Double.parseDouble(matcher.group(1));
		}
		throw new IllegalArgumentException(
				"Cannot compute priority for: " + attributeValue);
	}
}
