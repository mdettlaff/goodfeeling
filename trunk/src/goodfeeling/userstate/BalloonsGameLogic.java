package goodfeeling.userstate;

import goodfeeling.userstate.Balloon.Operator;
import goodfeeling.util.Pair;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Logika gry Balloons. Zobacz na:
 * https://www.bbc.co.uk/labuk/results/braintestbritain/games/balloons.html
 */
public class BalloonsGameLogic {

	public static enum Response {
		CORRECT_ANSWER,
		INCORRECT_ANSWER,
		NO_ANSWER,
		;
	}

	public static final int MAX_NUMBER = 12;

	private Set<Balloon> activeBalloons = new HashSet<Balloon>();
	private StringBuilder currentAnswer = new StringBuilder();
	private Random random = new Random();

	private BalloonGameResults results = new BalloonGameResults();

	public Balloon nextBalloon() {
		Balloon newBalloon = createBalloon();
		activeBalloons.add(newBalloon);
		return newBalloon;
	}

	public void balloonFlownAway(Balloon balloon) {
		activeBalloons.remove(balloon);
	}

	public void enterDigit(int digit) {
		if (!(0 <= digit && digit < 10)) {
			throw new IllegalArgumentException(
					"Parameter digit must be a digit (" + digit + " given).");
		}
		this.currentAnswer.append(digit);
	}

	public Response confirmAnswer() {
		if (currentAnswer.length() == 0) {
			cancelInput();
			return Response.NO_ANSWER;
		}
		for (Balloon balloon : activeBalloons) {
			if (balloon.checkAnswer(Integer.parseInt("" + currentAnswer))) {
				cancelInput();
				activeBalloons.remove(balloon);
				results.addCorrectAnswer();
				return Response.CORRECT_ANSWER;
			}
		}
		cancelInput();
		results.addInorrectAnswer();
		return Response.INCORRECT_ANSWER;
	}

	public void cancelInput() {
		currentAnswer = new StringBuilder();
	}

	private Balloon createBalloon() {
		int x = random.nextInt(MAX_NUMBER + 1);
		int y = random.nextInt(MAX_NUMBER + 1);
		Operator[] allOperators = Balloon.Operator.values();
		Operator operator = allOperators[random.nextInt(allOperators.length)];
		return new Balloon(new Pair<Integer, Integer>(x, y), operator);
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public BalloonGameResults getResults() {
		return results ;
	}
}
