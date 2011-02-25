package goodfeeling.userstate.balloon;

import android.content.Intent;

public class BalloonResult {
	
	public static final String BALLOON_RESULT_NAME = "BalloonResult";
	
	private int correct;
	private int incorrect;
	private int all;
	
	public BalloonResult() {
		this.correct = 0;
		this.incorrect = 0;
		this.all = 0;
	}
	
	public BalloonResult(Intent intent) throws BalloonResultException {
		if(intent == null)
			throw new BalloonResultException();
		int[] result = intent.getIntArrayExtra(BALLOON_RESULT_NAME);
		if(result == null || result.length != 3)
			throw new BalloonResultException();
		this.correct = result[0];
		this.incorrect = result[1];
		this.all = result[2];
	}
	
	public void put(Intent intent) {
		if(intent == null)
			return;
		int result[] = {this.correct, this.incorrect, this.all};
		intent.putExtra(BALLOON_RESULT_NAME, result);
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
