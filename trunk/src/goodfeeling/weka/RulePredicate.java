package goodfeeling.weka;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Predicate consisting of attribute mappings joined with AND operator.
 * For example: foo = 1 and bar = 2 and baz = 5.
 */
public class RulePredicate {

	private Map<String, String> attributes;

	public RulePredicate() {
		attributes = new LinkedHashMap<String, String>();
	}

	public void putAttribute(String name, String value) {
		attributes.put(name, value);
	}

	public Set<Map.Entry<String, String>> getAttributeMappings() {
		return attributes.entrySet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
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
		RulePredicate other = (RulePredicate) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();
			String attributeValue = entry.getValue();
			sb.append(attributeName + "=" + attributeValue);
			sb.append(" ");
		}
		return sb.toString().replaceFirst(" $", "");
	}
}
