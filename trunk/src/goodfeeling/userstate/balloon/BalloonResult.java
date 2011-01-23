package goodfeeling.userstate.balloon;

public class BalloonResult {
	private int correct;
	private int incorrect;
	
	public BalloonResult() {
		this.correct = 0;
		this.incorrect = 0;
	}
	
	public int getCorrect() {
		return this.correct;
	}
	
	public int getIncorrect() {
		return this.incorrect;
	}
	
	public void incCorrect() {
		this.correct++;
	}
	
	public void incIncorrect() {
		this.incorrect++;
	}
}
