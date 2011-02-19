package goodfeeling.gui;

import goodfeeling.common.AndroidFileIO;
import goodfeeling.db.DbHandler;
import goodfeeling.db.Record;
import goodfeeling.db.RecordActivity;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

public class Activities extends Activity {
	AutoCompleteTextView activityName;
	TimePicker fromTimePicker, untilTimePicker;
	RatingBar intensity;
	
	String intensityValue = "";
	String[] opts = { "Low", "Average", "High" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);

		intensity = (RatingBar) findViewById(R.id.intensity);
		intensity.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
		    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		    	intensityValue = opts[(int) (rating-1)];
		    }
		});

		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!save())
					DisplayToast("Please fill all fields.");
			}
		});

		activityName = (AutoCompleteTextView) findViewById(R.id.activityName);
		activityName.setThreshold(1);
		updateAutocomplete();

		fromTimePicker = (TimePicker) findViewById(R.id.fromTimePicker);
		fromTimePicker.setIs24HourView(true);

		untilTimePicker = (TimePicker) findViewById(R.id.untilTimePicker);
		untilTimePicker.setIs24HourView(true);
	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private boolean save() {
		String activityNameString = activityName.getText().toString();
		if (activityNameString.equals(""))
			return false;
		
		if (intensityValue.equals(""))
			return false;

		// cichaczem pomijamy sytuację gdy fromTime > untilTime
		int duration = (untilTimePicker.getCurrentHour() - fromTimePicker
				.getCurrentHour())
				* 60
				+ (untilTimePicker.getCurrentMinute() - fromTimePicker
						.getCurrentMinute());
		
		if (duration <= 0)
			return false;

		RecordActivity activity = new RecordActivity();
		activity.name = activityNameString;
		activity.startHour = fromTimePicker.getCurrentHour();
		activity.duration = duration;
		activity.intensivity = intensityValue;
		
		try {
			DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
			Record today = dbHandler.getRecord(Calendar.getInstance());
			today.addActivity(activity);
			dbHandler.addOrUpdateRecord(today);

			dbHandler.addToActivityDictionary(new goodfeeling.db.Activity(
					activityNameString));

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		activityName.setText("");
		activityName.requestFocus();
		updateAutocomplete();
		return true;
	}

	private String[] getActivitiesDict() {
		ArrayList<String> activities = new ArrayList<String>();
		try {
			DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
			ArrayList<goodfeeling.db.Activity> dbActivities = dbHandler
					.getActivitiesDictionaryList();
			for (int i = 0; i < dbActivities.size(); i++) {
				activities.add(dbActivities.get(i).name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String tmp[] = new String[activities.size()];
		tmp = activities.toArray(tmp);
		return tmp;
	}

	private void updateAutocomplete() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				getActivitiesDict());
		activityName.setAdapter(adapter);
	}
}