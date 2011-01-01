package goodfeeling.userstate;

import goodfeeling.util.Picture;
import goodfeeling.util.Picture.Category;

import java.util.ArrayList;
import java.util.List;

public class AttitudeTestResults {

	private List<Picture> selectedPictures = new ArrayList<Picture>();

	public AttitudeTestResults(List<Picture> selectedPictures) {
		this.selectedPictures = selectedPictures;
	}

	public int countSelected(Category category) {
		int howManyTimesWasCategorySelected = 0;
		for (Picture pic : selectedPictures) {
			if (pic.category == category) {
				howManyTimesWasCategorySelected++;
			}
		}
		return howManyTimesWasCategorySelected;
	}
}
