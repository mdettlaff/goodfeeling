package goodfeeling.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Diary extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary);

		OnClickListener notImplemented = new View.OnClickListener() {
			public void onClick(View v) {
				// startActivity(new Intent(v.getContext(), .class));
				DisplayToast(getString(R.string.gui_err_not_implemented));
			}
		};
		
		ImageButton activities = (ImageButton) findViewById(R.id.activity_btn);
		activities.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), Activities.class));
			}
		});

		ImageButton meals = (ImageButton) findViewById(R.id.meals_btn);
		meals.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), Meals.class));
			}
		});

		ImageButton sleep = (ImageButton) findViewById(R.id.sleep_btn);
		sleep.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), Sleep.class));
			}
		});
		

		/*ImageButton work = (ImageButton) findViewById(R.id.work_btn);
		work.setOnClickListener(notImplemented);
		ImageButton relax = (ImageButton) findViewById(R.id.relax_btn);
		relax.setOnClickListener(notImplemented);
		ImageButton health = (ImageButton) findViewById(R.id.health_btn);
		health.setOnClickListener(notImplemented);*/
		

		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
