package goodfeeling.gui;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class Sleep extends Activity {

	int fromMin, fromHour, untilMin, untilHour;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sleep);
		
		Calendar calendar = Calendar.getInstance();

		fromMin = calendar.getTime().getMinutes();
		untilMin = calendar.getTime().getMinutes();
		fromHour = calendar.getTime().getHours();
		untilHour = calendar.getTime().getHours();

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
				DisplayToast("W przyszłości po kliknięciu tego przycisku dane zostaną zapisane: sen od "
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
		fromTimePicker.setIs24HourView(true);
		fromTimePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						fromMin = minute;
						fromHour = hourOfDay;
					}
				});
		TimePicker untilTimePicker = (TimePicker) findViewById(R.id.untilTimePicker);
		untilTimePicker.setIs24HourView(true);
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
