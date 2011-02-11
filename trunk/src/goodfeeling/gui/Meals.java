package goodfeeling.gui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Meals extends ListActivity {

	private CheckBoxifiedTextListAdapter cbla;
	private String[] items = { "Food 1", "Food 2", "Food 3", "Food 4" };

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.meals);

		cbla = new CheckBoxifiedTextListAdapter(this);
		for (int k = 0; k < items.length; k++) {
			cbla.addItem(new CheckBoxifiedText(items[k], false));
		}
		setListAdapter(cbla);

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
				DisplayToast("W przyszłości po kliknięciu tego przycisku dane zostaną zapisane");
			}
		});
	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
