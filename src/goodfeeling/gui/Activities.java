package goodfeeling.gui;

import goodfeeling.common.AndroidFileIO;
import goodfeeling.db.DbHandler;
import goodfeeling.db.Record;
import goodfeeling.db.RecordActivity;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class Activities extends Activity {
	String[] activities = { "spacer", "ryby", "rower", "basen", "łyżwy",
			"rolki", "narty" };

	AutoCompleteTextView activityName;
	TimePicker fromTimePicker, untilTimePicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);

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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, activities);
		activityName = (AutoCompleteTextView) findViewById(R.id.activityName);
		activityName.setThreshold(1);
		activityName.setAdapter(adapter);

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
		System.out.println(activityNameString);
		if (activityNameString.equals("")) return false;
		
		// TODO jeśli druga godzina jest mniejsza to jest fail
		int duration = (untilTimePicker.getCurrentHour() - fromTimePicker
				.getCurrentHour()) * 60	+ (untilTimePicker.getCurrentMinute()
						- fromTimePicker.getCurrentMinute());
		System.out.println(duration);
		if (duration==0) return false;
		
		DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
		try {
			RecordActivity activity = new RecordActivity();
			activity.name = activityNameString;
			activity.startHour = fromTimePicker.getCurrentHour();
			activity.duration = duration;
			activity.intensivity = "Medium"; // ??

			Record today = dbHandler.getRecord(Calendar.getInstance());
			today.addActivity(activity);
			dbHandler.addOrUpdateRecord(today);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}