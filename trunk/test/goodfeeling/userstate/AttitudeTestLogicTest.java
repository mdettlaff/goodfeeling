package goodfeeling.userstate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import goodfeeling.util.Pair;
import goodfeeling.util.Picture;

import java.awt.Color;
import java.util.LinkedList;

import org.junit.Test;

public class AttitudeTestLogicTest {

	@Test
	public void test() {
		LinkedList<Pair<Picture, Picture>> pictures =
			new LinkedList<Pair<Picture, Picture>>();
		pictures.add(new Pair<Picture, Picture>(
				new Picture(Picture.Category.HAPPY, Color.GREEN),
				new Picture(Picture.Category.SAD, Color.BLACK)));
		pictures.add(new Pair<Picture, Picture>(
				new Picture(Picture.Category.SAD, Color.BLUE),
				new Picture(Picture.Category.NEUTRAL, Color.GRAY)));
		pictures.add(new Pair<Picture, Picture>(null, null));
		pictures.add(new Pair<Picture, Picture>(
				new Picture(Picture.Category.SAD, Color.WHITE),
				new Picture(Picture.Category.HAPPY, Color.YELLOW)));

		AttitudeTestLogic attitudeTest = new AttitudeTestLogic(pictures);
		assertFalse(attitudeTest.isTestFinished());
		attitudeTest.selectTopPicture();
		attitudeTest.selectBottomPicture();
		attitudeTest.selectNeitherPicture();
		attitudeTest.selectBottomPicture();
		assertTrue(attitudeTest.isTestFinished());
		AttitudeTestResults results = attitudeTest.getResults();
		assertEquals(2, results.countSelected(Picture.Category.HAPPY));
		assertEquals(0, results.countSelected(Picture.Category.SAD));
		assertEquals(1, results.countSelected(Picture.Category.NEUTRAL));
	}
}
