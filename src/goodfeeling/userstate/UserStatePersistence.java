package goodfeeling.userstate;

import goodfeeling.db.DbHandler;
import goodfeeling.db.Record;
import goodfeeling.db.TestResult;
import goodfeeling.userstate.balloon.BalloonResult;
import goodfeeling.userstate.exercises.ExercisesResult;
import goodfeeling.userstate.picture_test.PictureTestResult;

import java.util.Calendar;
import java.util.List;

public class UserStatePersistence {

	private DbHandler dbHandler;

	public UserStatePersistence(DbHandler dbHandler) {
		this.dbHandler = dbHandler;
	}

	public void persistResults(
			List<PictureTestResult> pictureTestResults,
			List<BalloonResult> balloonTestResults,
			List<ExercisesResult> exercisesTestResults) {
		try {
			int pictureTestScore = computePictureTestScore(pictureTestResults);
			int balloonsTestScore = computeBalloonsTestScore(balloonTestResults);
			int exercisesTestScore = computeExercisesTestScore(exercisesTestResults);
			
			
			Record record = dbHandler.getRecord(Calendar.getInstance());
			record.moodRates.add(new TestResult("" + pictureTestScore));
			record.mentalRates.add(new TestResult("" + balloonsTestScore));
			record.physicalRates.add(new TestResult("" + exercisesTestScore));
			dbHandler.addOrUpdateRecord(record);
		} catch (Exception e) {
			throw new RuntimeException("Cannot write user state to the database", e);
		}
	}

	private int computeExercisesTestScore(List<ExercisesResult> exercisesTestResults) {
		int sumAll = 0;
		int sumBest = 0;
		for (ExercisesResult result : exercisesTestResults) {
			sumBest += result.getBestCount();
			sumAll += result.getCount();
		}
		return getPercentageScore(sumAll, sumBest);
	}

	public CharSequence getCurrentMood() {
		Record record = dbHandler.getRecord(Calendar.getInstance());
		return record.getLastMoodRate() + "%";
	}

	public CharSequence getCurrentMentalPerformance() {
		Record record = dbHandler.getRecord(Calendar.getInstance());
		return record.getLastMentalRate() + "%";
	}

	private static int computePictureTestScore(
			List<PictureTestResult> pictureTestResults) {
		int sumPositiveAnswers = 0;
		int sumAllAnswers = 0;
		for (PictureTestResult result : pictureTestResults) {
			for (int category = 0; category < result.getCategoryCount(); category++) {
				sumPositiveAnswers += result.getAnswerPositive(category);
				sumAllAnswers += result.getAnswer(category);
			}
		}
		return getPercentageScore(sumPositiveAnswers, sumAllAnswers);
	}

	private static int computeBalloonsTestScore(
			List<BalloonResult> balloonTestResults) {
		int sumCorrect = 0;
		int sumAll = 0;
		for (BalloonResult result : balloonTestResults) {
			sumCorrect += result.getCorrect();
			sumAll += result.getAll();
		}
		return getPercentageScore(sumCorrect, sumAll);
	}

	private static int getPercentageScore(int correct, int all) {
		return (int)(((double)correct) / all * 100);
	}
}
