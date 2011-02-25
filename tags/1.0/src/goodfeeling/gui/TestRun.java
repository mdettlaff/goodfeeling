package goodfeeling.gui;

import java.util.ArrayList;
import java.util.List;
import goodfeeling.gui.R;
import goodfeeling.common.AndroidFileIO;
import goodfeeling.db.DbHandler;
import goodfeeling.userstate.UserStatePersistence;
import goodfeeling.userstate.ResultException;
import goodfeeling.userstate.balloon.Balloon;
import goodfeeling.userstate.balloon.BalloonResult;
import goodfeeling.userstate.exercises.Exercises;
import goodfeeling.userstate.exercises.ExercisesResult;
import goodfeeling.userstate.picture_test.PictureTest;
import goodfeeling.userstate.picture_test.PictureTestResult;
import goodfeeling.userstate.swing_test.SwingTest;
import goodfeeling.userstate.swing_test.SwingTestResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestRun extends Activity {

	private final String errFormatException = ">>> GoodFeeling:TestRun:onActivityResult: %s";
	private final String errFormatResultCode = ">>> GoodFeeling:TestRun:onActivityResult: %s unknown result code: %d";
	private final String errFormatRequestCode = ">>> GoodFeeling:TestRun:onActivityResult: Unknown request code: %d";

	private final int TEST_PICTURE_TEST = 0;
	private final int TEST_BALLOON = 1;
	private final int TEST_EXERCISES = 2;
	private final int TEST_SWING_TEST = 3;

	private final int TEST_NUM = 6;

	private int test[];

	private int testStep;

	private List<PictureTestResult> pictureTestResults;
	private List<BalloonResult> balloonTestResults;
	private List<ExercisesResult> exercisesTestResults;
	private List<SwingTestResult> swingTestResults;

	private boolean isWholeTestFinished;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		test = new int[TEST_NUM];
		test[0] = TEST_PICTURE_TEST;
		test[1] = TEST_BALLOON;
		test[2] = TEST_SWING_TEST;
		test[3] = TEST_PICTURE_TEST;
		test[4] = TEST_BALLOON;
		test[5] = TEST_EXERCISES;

		this.testStep = 0;

		pictureTestResults = new ArrayList<PictureTestResult>();
		balloonTestResults = new ArrayList<BalloonResult>();
		exercisesTestResults = new ArrayList<ExercisesResult>();
		swingTestResults = new ArrayList<SwingTestResult>();

		isWholeTestFinished = false;

		runNextTest();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			switch(requestCode) {
				case TEST_PICTURE_TEST:
					switch(resultCode) {
						case RESULT_OK:
							pictureTestResults.add(new PictureTestResult(data));
							break;
						default:
							System.err.printf(errFormatResultCode, "PictureTest", resultCode);
					}
					break;
				case TEST_BALLOON:
					switch(resultCode) {
						case RESULT_OK:
							balloonTestResults.add(new BalloonResult(data));
							break;
						default:
							System.err.printf(errFormatResultCode, "Balloon", resultCode);
					}
					break;
				case TEST_EXERCISES:
					switch(resultCode) {
						case RESULT_OK:
							exercisesTestResults.add(new ExercisesResult(data));
							break;
						default:
							System.err.printf(errFormatResultCode, "Exercises", resultCode);
					}
					break;
				case TEST_SWING_TEST:
					switch(resultCode) {
						case RESULT_OK:
							swingTestResults.add(new SwingTestResult(data));
							break;
						default:
							System.err.printf(errFormatResultCode, "SwingTest", resultCode);
					}
					break;
				default:
					System.err.printf(errFormatRequestCode, requestCode);
			}
		} catch (ResultException e) {
			System.err.printf(errFormatException, e.getClass().getSimpleName());
		}
		runNextTest();
	}

	// private

	private void runNextTest() {
		if(this.testStep >= test.length) {
			if(!isWholeTestFinished) {
				onWholeTestFinished();
				isWholeTestFinished = true;
			}
			finish();
			return;
		}
		Intent intent;
		switch(this.test[this.testStep]) {
			case TEST_PICTURE_TEST:
				intent = new Intent(this, PictureTest.class);
			break;
			case TEST_BALLOON:
				intent = new Intent(this, Balloon.class);
			break;
			case TEST_EXERCISES:
				intent = new Intent(this, Exercises.class);
			break;
			case TEST_SWING_TEST:
				intent = new Intent(this, SwingTest.class);
			break;
			default:
				this.testStep++;
				runNextTest();
				return;
		}
		startActivityForResult(intent, this.test[this.testStep]);
		this.testStep++;
	}

	private void onWholeTestFinished() {
		UserStatePersistence persistence = new UserStatePersistence(new DbHandler(new AndroidFileIO(this)));
		persistence.persistResults(pictureTestResults, balloonTestResults, exercisesTestResults, swingTestResults);
	}
}
