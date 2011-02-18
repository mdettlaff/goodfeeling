package goodfeeling.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class GoodFeeling extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		OnClickListener notImplemented = new View.OnClickListener() {
			public void onClick(View v) {
				// startActivity(new Intent(v.getContext(), .class));
				DisplayToast(getString(R.string.gui_err_not_implemented));
			}
		};
		
		ImageButton suggestions = (ImageButton) findViewById(R.id.check_suggestions_btn);
		suggestions.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), Suggestions.class));
			}
		});
		
		ImageButton diary = (ImageButton) findViewById(R.id.fill_diary_btn);
		diary.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), Diary.class));
			}
		});

		ImageButton test = (ImageButton) findViewById(R.id.make_test_btn);
		test.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), TestRun.class));
			}
		});

		/*
		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		*/
	}

	public void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
}