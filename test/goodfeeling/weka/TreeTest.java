package goodfeeling.weka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TreeTest {

	@Test
	public void testEquality() {
		assertEquals(new Tree("A"), new Tree("A"));
		assertEquals(createSimpleTree(), createSimpleTree());
	}

	@Test
	public void testInequality() {
		Tree different = new Tree("A");
		Tree B = new Tree("B");
		Tree C = new Tree("C");
		Tree D = new Tree("D");
		different.addChild(B);
		different.addChild(C);
		C.addChild(D);
		assertFalse(new Tree("A").equals(new Tree("B")));
		assertFalse(createSimpleTree().equals(different));
	}

	@Test
	public void testInequalityWithLabeledEdge() {
		Tree A1 = new Tree("A");
		Tree B1 = new Tree("B");
		A1.addChild(B1);
		Tree A2 = new Tree("A");
		Tree B2 = new Tree("B");
		A2.addChild(B2, "foo");
		assertFalse(A1.equals(A2));
	}

	@Test
	public void testTreeToString() {
		Tree tree = createSimpleTree();
		final String actual = tree.toString();
		final String expected =
			"A\n" +
			"|-- B\n" +
			"`-- C\n" +
			"    |-- D\n" +
			"    `-- E";
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAllPathsFromRootToLeaf() {
		Tree A = new Tree("A");
		Tree B = new Tree("B");
		Tree C = new Tree("C");
		Tree D = new Tree("D");
		Tree E = new Tree("E");
		A.addChild(B);
		A.addChild(C);
		C.addChild(D);
		C.addChild(E);
		List<List<Tree>> actual = A.getAllPathsFromRootToLeaf();
		List<List<Tree>> expected = new ArrayList<List<Tree>>();
		expected.add(Arrays.asList(A, B));
		expected.add(Arrays.asList(A, C, D));
		expected.add(Arrays.asList(A, C, E));
		assertEquals(expected , actual);
	}

	private static Tree createSimpleTree() {
		//    A
		//  B   C
		//     D E
		Tree A = new Tree("A");
		Tree B = new Tree("B");
		Tree C = new Tree("C");
		Tree D = new Tree("D");
		Tree E = new Tree("E");
		A.addChild(B);
		A.addChild(C);
		C.addChild(D);
		C.addChild(E);
		return A;
	}

	@Test
	public void testParseDOT() {
		final String DOT =
			"digraph foo {\n" +
			"  A->B\n" +
			"  A->C\n" +
			"  C->D\n" +
			"  C->E\n" +
			"}";
		Tree actual = Tree.parseDOT(DOT);
		Tree expected = createSimpleTree();
		assertEquals(expected, actual);
	}

	@Test
	public void testParseDOTWithLabeledNodes() {
		final String DOT =
			"digraph foo {\n" +
			"N0 [label=\"race\" ]\n" +
			"N0->N1\n" +
			"N1 [label=\">50K\" shape=box style=filled ]\n" +
			"}";
		Tree actual = Tree.parseDOT(DOT);
		Tree expected = new Tree("N0");
		expected.setLabel("race");
		Tree highIncome = new Tree("N1");
		highIncome.setLabel(">50K");
		expected.addChild(highIncome);
		assertEquals(expected, actual);
	}

	@Test
	public void testParseDOTWithLabeledEdge() {
		final String DOT =
			"digraph foo {\n" +
			"N0\n" +
			"N0->N1 [label=\"= white\" ]\n" +
			"N1 [label=\">50K\" ]\n" +
			"}";
		Tree actual = Tree.parseDOT(DOT);
		Tree expected = new Tree("N0");
		Tree highIncome = new Tree("N1");
		highIncome.setLabel(">50K");
		expected.addChild(highIncome, "= white");
		assertEquals(expected, actual);
	}

	@Test
	public void testEdgeLabel() {
		Tree A = new Tree("A");
		Tree B = new Tree("B");
		A.addChild(B, "foo");
		assertEquals("foo", A.getEdgeLabel(B));
		assertNull(B.getEdgeLabel(A));
	}

	@Test
	public void dontMatchEdgeAsSingleNode() {
		final String DOT =
			"digraph foo {\n" +
			"N0->N1 [label=\"= white\"]\n" +
			"}";
		Tree actual = Tree.parseDOT(DOT);
		Tree N0 = new Tree("N0");
		Tree N1 = new Tree("N1");
		N0.addChild(N1, "= white");
		assertEquals(N0, actual);
	}

	@Test
	public void testParseDOTSimpleFromWeka() {
		final String dot =
			"digraph J48Tree {\n" +
			"N0 [label=\"race\" ]\n" +
			"N0->N1 [label=\"= white\"]\n" +
			"N1 [label=\">50K (2.0)\" shape=box style=filled ]\n" +
			"N0->N2 [label=\"= black\"]\n" +
			"N2 [label=\"<50K (2.0)\" shape=box style=filled ]\n" +
			"}";
		Tree actual = Tree.parseDOT(dot);
		Tree expected = new Tree("race");
		Tree highIncome = new Tree(">50K (2.0)");
		Tree lowIncome = new Tree("<50K (2.0)");
		expected.addChild(highIncome, "= white");
		expected.addChild(lowIncome, "= black");
		assertEquals("= white", actual.getEdgeLabel(highIncome));
		assertEquals(expected, actual);
	}
}
