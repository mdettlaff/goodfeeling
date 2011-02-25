package goodfeeling.userstate.swing_test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.content.Intent;

public class SwingTestResult implements Comparator<Float> {

	private final String SWING_TEST_RESULT_NAME = "SwingTestResult";
	
	private ArrayList<Float> results;
	private float result;
	private boolean is_result_change;
	
	public SwingTestResult() {
		this.results = new ArrayList<Float>();
		this.result = 0.0f;
		this.is_result_change = false;
	}
	
	public SwingTestResult(Intent intent) throws SwingTestResultException {
		if(intent == null)
			throw new SwingTestResultException();
		this.result = intent.getFloatExtra(SWING_TEST_RESULT_NAME, Float.NaN);
		if(this.result == Float.NaN)
			throw new SwingTestResultException();
		this.is_result_change = false;
		this.results = new ArrayList<Float>();
	}
	
	public void put(Intent intent) {
		if(intent == null)
			return;
		result();
		intent.putExtra(SWING_TEST_RESULT_NAME, this.result);
	}
	
	public float getResult() {
		result();
		return this.result;
	}
	
	public void addResult(Float result) {
		this.results.add(result);
		this.is_result_change = true;
	}
	
	// private
	
	private void result() {
		if(this.is_result_change) {
			if(this.results.size() > 0) {
				Collections.sort(this.results, this);
				this.result = this.results.get(this.results.size() / 2);
			}
			this.is_result_change = false;
		}
	}

	// implements Comparator

	@Override
	public int compare(Float f1, Float f2) {
		return f1.compareTo(f2);
	}
}
