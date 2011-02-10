package goodfeeling.weka;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Drawable;
import weka.core.Instances;

class DecisionTreeRulesFinder implements IRulesFinder {

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
				consequent.putAttribute(classAttributeName, attributeValue);
				Rule rule = new Rule(antecedent, consequent, 1);
				rules.add(rule);
			}
		}
		return rules;
	}
}
