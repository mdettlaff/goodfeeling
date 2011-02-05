package goodfeeling.weka;

public class Rule {

	private final RulePredicate antecedent;
	private final RulePredicate consequent;

	public Rule(RulePredicate antecedent, RulePredicate consequent) {
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	public RulePredicate getAntecedent() {
		return antecedent;
	}

	public RulePredicate getConsequent() {
		return consequent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((antecedent == null) ? 0 : antecedent.hashCode());
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
		if (consequent == null) {
			if (other.consequent != null)
				return false;
		} else if (!consequent.equals(other.consequent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return antecedent + " ==> " + consequent;
	}
}
