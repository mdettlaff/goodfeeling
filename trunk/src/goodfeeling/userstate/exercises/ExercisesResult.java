package goodfeeling.userstate.exercises;

import android.content.Intent;

public class ExercisesResult {
	
	public static final String EXERCISES_RESULT_NAME = "ExercisesResult";
	
	private int count;
	
	public ExercisesResult() {
		this.count = 0;
	}
	
	public ExercisesResult(Intent intent) throws ExercisesResultException {
		if(intent == null)
			throw new ExercisesResultException();
		this.count = intent.getIntExtra(EXERCISES_RESULT_NAME, -1);
		if (this.count == -1) throw new ExercisesResultException();
	}
	
	public void put(Intent intent) {
		if(intent == null)
			return;
		intent.putExtra(EXERCISES_RESULT_NAME, this.count);
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getBestCount() {
		return 50;
	}
	
	
}
