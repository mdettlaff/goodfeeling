package goodfeeling.userstate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import goodfeeling.util.Pair;

import org.junit.Test;

public class BalloonsGameLogicTest {

	@Test
	public void testSimpleGameSession() {
		BalloonsGameLogic game = new BalloonsGameLogic();
		game.setRandom(new FakeRandom());
		Balloon balloon1 = game.nextBalloon();
		assertEquals(balloon1.operator, Balloon.Operator.PLUS);
		assertEquals(balloon1.numbers, new Pair<Integer, Integer>(4, 4));
		game.enterDigit(9);
		game.cancelInput();
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.NO_ANSWER);
		game.enterDigit(8);
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.CORRECT_ANSWER);
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.NO_ANSWER);
		game.enterDigit(8);
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.INCORRECT_ANSWER);
		Balloon balloon2 = game.nextBalloon();
		assertEquals(balloon2.operator, Balloon.Operator.MINUS);
		assertEquals(balloon2.numbers, new Pair<Integer, Integer>(3, 2));
		Balloon balloon3 = game.nextBalloon();
		assertEquals(balloon3.operator, Balloon.Operator.TIMES);
		assertEquals(balloon3.numbers, new Pair<Integer, Integer>(4, 4));
		game.enterDigit(1);
		game.enterDigit(6);
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.CORRECT_ANSWER);
		game.enterDigit(0);
		game.balloonFlownAway(balloon2);
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.INCORRECT_ANSWER);
		game.enterDigit(3);
		assertTrue(game.confirmAnswer() == BalloonsGameLogic.Response.INCORRECT_ANSWER);
	}
}
