package goodfeeling.userstate;

public class BalloonGameResults {

	private int correctAnswersCount;
	private int incorrectAnswersCount;

	public void addCorrectAnswer() {
		correctAnswersCount++;
	}

	public void addInorrectAnswer() {
		incorrectAnswersCount++;
	}

	public int getCorrectAnswersCount() {
		return correctAnswersCount;
	}

	public int getIncorrectAnswersCount() {
		return incorrectAnswersCount;
	}
}
