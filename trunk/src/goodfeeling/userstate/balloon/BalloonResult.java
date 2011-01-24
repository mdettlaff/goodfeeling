package goodfeeling.userstate.balloon;

public class BalloonResult {
	private int correct;
	private int incorrect;
	private int all;
	
	public BalloonResult() {
		this.correct = 0;
		this.incorrect = 0;
		this.all = 0;
	}
	
	public int getCorrect() {
		return this.correct;
	}
	
	public int getIncorrect() {
		return this.incorrect;
	}
	
	public int getAll() {
		return this.all;
	}
	
	public void incCorrect() {
		this.correct++;
	}
	
	public void incIncorrect() {
		this.incorrect++;
	}
	
	public void incAll() {
		this.all++;
	}
}
