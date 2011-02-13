package goodfeeling.weka;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;

class WekaUtils {

	private WekaUtils() {
	}

	public static Instances applyFilter(Instances data, Filter filter)
	throws Exception {
		filter.setInputFormat(data);
		for (int i = 0; i < data.numInstances(); i++) {
			filter.input(data.instance(i));
		}
		filter.batchFinished();
		Instances newData = filter.getOutputFormat();
		Instance processed;
		while ((processed = filter.output()) != null) {
			newData.add(processed);
		}
		return newData;
	}
}
