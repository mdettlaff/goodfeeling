package goodfeeling.weka;

import java.util.List;

import weka.core.Instances;

interface IRulesFinder {

	public List<Rule> findRules(Instances data) throws Exception;
}
