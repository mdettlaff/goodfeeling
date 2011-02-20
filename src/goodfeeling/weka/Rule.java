package goodfeeling.weka;

/**
 * A logical rule deduced from data table. It has the form:
 * <pre>
 * p1 = q1 ^ p2 = q2 ^ ... =&gt; r = s
 * </pre>
 */
public class Rule implements Comparable<Rule> {

	private final RulePredicate antecedent;
	private final RulePredicate consequent;
	private final double confidence;

	public Rule(RulePredicate antecedent, RulePredicate consequent,
			double confidence) {
		this.antecedent = antecedent;
		this.consequent = consequent;
		if (confidence < 0 || confidence > 1) {
			throw new IllegalArgumentException(
					"Confidence must be a value between 0 and 1.");
		}
		this.confidence = confidence;
	}

	public RulePredicate getAntecedent() {
		return antecedent;
	}

	public RulePredicate getConsequent() {
		return consequent;
	}

	/**
	 * A value between 0 and 1 roughly corresponding the probability of
	 * this rule being true.
	 */
	public double getConfidence() {
		return confidence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((antecedent == null) ? 0 : antecedent.hashCode());
		long temp;
		temp = Double.doubleToLongBits(confidence);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((consequent == null) ? 0 : consequent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (antecedent == null) {
			if (other.antecedent != null)
				return false;
		} else if (!antecedent.equals(other.antecedent))
			return false;
		if (Math.abs(confidence - other.confidence) > 0.001)
			return false;
		if (consequent == null) {
			if (other.consequent != null)
				return false;
		} else if (!consequent.equals(other.consequent))
			return false;
		return true;
	}

	@Override
	public int compareTo(Rule other) {
		if (getConfidence() > other.getConfidence()) {
			return 1;
		} else if (getConfidence() < other.getConfidence()) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		String formattedConfidence = String.format("%.3f", confidence);
		return antecedent + " ==> " + consequent +
		" (conf: " + formattedConfidence + ")";
	}
}
