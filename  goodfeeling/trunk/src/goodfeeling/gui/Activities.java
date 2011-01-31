package goodfeeling.gui;

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
	String[] activities = { "spacer", "rower", "basen", "łyżwy", "rolki",
			"narty" };

	int fromMin, fromHour, untilMin, untilHour;
	String activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);

		Calendar kalendarz = Calendar.getInstance();

		fromMin = kalendarz.getTime().getMinutes();
		untilMin = kalendarz.getTime().getMinutes();
		fromHour = kalendarz.getTime().getHours();
		untilHour = kalendarz.getTime().getHours();

		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, activities);

		AutoCompleteTextView activityName = (AutoCompleteTextView) findViewById(R.id.activityName);
		activityName.setThreshold(1);
		activityName.setAdapter(adapter);
		activityName
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						activity = (String) parent.getItemAtPosition(position);
					}
				});

		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DisplayToast("W przyszłości po kliknięciu tego przycisku dane zostaną zapisane: "
						+ activity
						+ " od "
						+ String.format("%02d", fromHour)
						+ ':'
						+ String.format("%02d", fromMin)
						+ " do "
						+ String.format("%02d", untilHour)
						+ ':'
						+ String.format("%02d", untilMin));
			}
		});

		TimePicker fromTimePicker = (TimePicker) findViewById(R.id.fromTimePicker);
		fromTimePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						fromMin = minute;
						fromHour = hourOfDay;
					}
				});

		TimePicker untilTimePicker = (TimePicker) findViewById(R.id.untilTimePicker);
		untilTimePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						untilMin = minute;
						untilHour = hourOfDay;
					}
				});
	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
