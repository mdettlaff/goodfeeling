package goodfeeling.weka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tree {

	private static final String GRAPH_EDGE_REGEX =
		"\\s*(\\w+)\\s*->\\s*(\\w+).*";
	private static final String GRAPH_LABELED_EDGE_REGEX =
		"\\s*(\\w+)\\s*->\\s*(\\w+)\\s*.*?label=\"(.+?)\".*";
	private static final String GRAPH_SINGLE_NODE_REGEX =
		"\\s*(\\w+)\\s*.*?label=\"(.+?)\".*";

	private final String id;
	private Tree parent;
	private LinkedList<Tree> children;
	private String label;
	private String edgeLabelToParent;

	public Tree(String id) {
		children = new LinkedList<Tree>();
		this.id = id;
	}

	public String getLabel() {
		if (label != null) {
			return label;
		}
		return id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void addChild(Tree child) {
		children.add(child);
		child.parent = this;
	}

	public void addChild(Tree child, String edgeLabel) {
		addChild(child);
		child.setEdgeLabelToParent(edgeLabel);
	}

	public LinkedList<Tree> getChildren() {
		return new LinkedList<Tree>(children);
	}

	public Tree getParent() {
		return parent;
	}

	public Tree getRoot() {
		if (getParent() == null) {
			return this;
		} else {
			return getParent().getRoot();
		}
	}

	public String getEdgeLabel(Tree child) {
		return child.getEdgeLabelToParent();
	}

	private void setEdgeLabelToParent(String edgeLabel) {
		edgeLabelToParent = edgeLabel;
	}

	private String getEdgeLabelToParent() {
		return edgeLabelToParent;
	}

	/**
	 * Parses a tree given in the DOT format.
	 */
	public static Tree parseDOT(String dot) {
		Set<String> nodesIds = findNodesIds(dot);
		Map<String, Tree> idsToNodes = new HashMap<String, Tree>();
		for (String nodeId : nodesIds) {
			idsToNodes.put(nodeId, new Tree(nodeId));
		}
		setNodesLabels(idsToNodes, dot);
		addEdgesToNodes(idsToNodes, dot);
		return idsToNodes.entrySet().iterator().next().getValue().getRoot();
	}

	private static void addEdgesToNodes(
			Map<String, Tree> idsToNodes, String dot) {
		for (String line : dot.split("\n")) {
			boolean isEdgeLabeled = addLabeledEdge(idsToNodes, line);
			if (!isEdgeLabeled) {
				addUnlabeledEdge(idsToNodes, line);
			}
		}
	}

	private static boolean addLabeledEdge(
			Map<String, Tree> idsToNodes, String line) {
		Pattern pattern = Pattern.compile(GRAPH_LABELED_EDGE_REGEX);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			Tree node1 = idsToNodes.get(matcher.group(1));
			Tree node2 = idsToNodes.get(matcher.group(2));
			String label = matcher.group(3);
			node1.addChild(node2, label);
			return true;
		}
		return false;
	}

	private static void addUnlabeledEdge(
			Map<String, Tree> idsToNodes, String line) {
		Pattern pattern = Pattern.compile(GRAPH_EDGE_REGEX);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			Tree node1 = idsToNodes.get(matcher.group(1));
			Tree node2 = idsToNodes.get(matcher.group(2));
			node1.addChild(node2);
		}
	}

	private static Set<String> findNodesIds(String dot) {
		Set<String> nodes = new HashSet<String>();
		Pattern patternEdge = Pattern.compile(GRAPH_EDGE_REGEX);
		for (String line : dot.split("\n")) {
			Matcher matcher = patternEdge.matcher(line);
			if (matcher.find()) {
				nodes.add(matcher.group(1));
				nodes.add(matcher.group(2));
			}
		}
		Pattern patternNode = Pattern.compile(GRAPH_SINGLE_NODE_REGEX);
		for (String line : dot.split("\n")) {
			Matcher matcher = patternNode.matcher(line);
			if (matcher.find()) {
				nodes.add(matcher.group(1));
			}
		}
		return nodes;
	}

	private static void setNodesLabels(Map<String, Tree> idsToNodes, String dot) {
		Pattern pattern = Pattern.compile(GRAPH_SINGLE_NODE_REGEX);
		for (String line : dot.split("\n")) {
			Matcher matcher = pattern.matcher(line);
			if (!line.matches(GRAPH_EDGE_REGEX) && matcher.find()) {
				Tree node = idsToNodes.get(matcher.group(1));
				String label = matcher.group(2);
				node.setLabel(label);
			}
		}
	}

	List<List<Tree>> getAllPathsFromRootToLeaf() {
		List<List<Tree>> paths = new ArrayList<List<Tree>>();
		if (getChildren().isEmpty()) {
			List<Tree> singleton = new LinkedList<Tree>();
			singleton.add(this);
			paths.add(singleton);
		}
		for (Tree child : getChildren()) {
			List<List<Tree>> pathsFromChild = child.getAllPathsFromRootToLeaf();
			for (List<Tree> path : pathsFromChild) {
				path.add(0, this);
				paths.add(path);
			}
		}
		return paths;
	}

	@Override
	public String toString() {
		return toString("");
	}

	private String toString(String leftSide) {
		StringBuilder result = new StringBuilder();
		String childLeftSide = "";
		if (getParent() != null) {
			result.append(leftSide + (isLastSibling() ? "`" : "|") + "-- ");
			childLeftSide = leftSide + (isLastSibling() ? " " : "|") + "   ";
		}
		result.append(getLabel());
		for (Tree child : getChildren()) {
			result.append("\n" + child.toString(childLeftSide));
		}
		return result.toString();
	}

	private boolean isLastSibling() {
		return getParent() == null
				|| equals(getParent().getChildren().getLast());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tree other = (Tree) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (getLabel() == null) {
			if (other.getLabel() != null)
				return false;
		} else if (!getLabel().equals(other.getLabel()))
			return false;
		if (getEdgeLabelToParent() == null) {
			if (other.getEdgeLabelToParent() != null)
				return false;
		} else if (!getEdgeLabelToParent().equals(other.getEdgeLabelToParent()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
