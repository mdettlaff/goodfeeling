package goodfeeling.userstate.picture_test;

import android.content.Intent;

public class PictureTestResult {
	
	public static final String PICTURE_TEST_RESULT_NAME_POSITIVE = "PictureTestResultPositive";
	public static final String PICTURE_TEST_RESULT_NAME = "PictureTestResult";
	
	private int categoryNumber;
	
	private int[] answersPositive;
	
	private int[] answers;
	
	public PictureTestResult(int categoryNumber) {
		this.categoryNumber = categoryNumber;
		this.answersPositive = new int[categoryNumber];
        this.answers = new int[categoryNumber];
        for(int i = 0; i < categoryNumber; i++) {
        	this.answersPositive[i] = 0;
        	this.answers[i] = 0;
        }
	}
	
	public PictureTestResult(Intent intent) throws PictureTestResultException {
		if(intent == null)
			throw new PictureTestResultException();
		int[] answersPositive = intent.getIntArrayExtra(PICTURE_TEST_RESULT_NAME_POSITIVE);
		int[] answers = intent.getIntArrayExtra(PICTURE_TEST_RESULT_NAME);
		if(answersPositive == null || answers == null || answersPositive.length != answers.length)
			throw new PictureTestResultException();
		this.categoryNumber = answersPositive.length;
		this.answersPositive = answersPositive;
        this.answers = answers;
	}
	
	public void put(Intent intent) {
		if(intent == null)
			return;
		intent.putExtra(PICTURE_TEST_RESULT_NAME_POSITIVE, this.answersPositive);
		intent.putExtra(PICTURE_TEST_RESULT_NAME, this.answers);
	}
	
	public int getCategoryNumber() {
		return this.categoryNumber;
	}
	
	public int getAnswerPositive(int category) {
		return this.answersPositive[category];
	}
	
	public int getAnswer(int category) {
		return this.answers[category];
	}
	
	public void incAnswerPositive(int category) {
		this.answersPositive[category]++;
	}
	
	public void incAnswer(int category) {
		this.answers[category]++;
	}
	
	public void decAnswerPositive(int category) {
		this.answersPositive[category]--;
	}
	
	public void decAnswer(int category) {
		this.answers[category]--;
	}
}
