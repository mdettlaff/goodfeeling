package goodfeeling.userstate;

import goodfeeling.util.Pair;
import goodfeeling.util.Picture;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AttitudeTestLogic {

	private LinkedList<Pair<Picture, Picture>> allPictures =
		new LinkedList<Pair<Picture, Picture>>();
	private Pair<Picture, Picture> currentPictures;
	private List<Picture> selectedPictures = new ArrayList<Picture>();

	public AttitudeTestLogic(LinkedList<Pair<Picture, Picture>> pictures) {
		this.allPictures = pictures;
		nextPairOfPictures();
	}

	public Picture getTopPicture() {
		return currentPictures.getFirst();
	}

	public Picture getBottomPicture() {
		return currentPictures.getSecond();
	}

	public void selectTopPicture() {
		selectPicture(currentPictures.getFirst());
	}

	public void selectBottomPicture() {
		selectPicture(currentPictures.getSecond());
	}

	public void selectNeitherPicture() {
		nextPairOfPictures();
	}

	public boolean isTestFinished() {
		return allPictures.isEmpty();
	}

	private void selectPicture(Picture picture) {
		selectedPictures.add(picture);
		if (!isTestFinished()) {
			nextPairOfPictures();
		}
	}

	private void nextPairOfPictures() {
		if (allPictures.isEmpty()) {
			throw new IllegalStateException(
					"No more pictures: the test is finished.");
		}
		currentPictures = allPictures.getFirst();
		allPictures.removeFirst();
	}

	public AttitudeTestResults getResults() {
		return new AttitudeTestResults(selectedPictures);
	}
}
