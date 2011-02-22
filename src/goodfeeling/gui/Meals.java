package goodfeeling.gui;

import goodfeeling.common.AndroidFileIO;
import goodfeeling.db.DbHandler;
import goodfeeling.db.Record;
import goodfeeling.db.RecordFood;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Meals view
 * 
 * Displays form for adding new meal to database. 
 */
public class Meals extends Activity {

	private Spinner unitSpinner, timeConsumedSpinner;
	private EditText amountEditText;
	private AutoCompleteTextView mealAutoCompleteTextView;
	
	/**
	 * Values for unit selection spinner.
	 */
	private String[] unitsItems = { "g","ml","pieces" };
	
	/**
	 * Values for timeConsumed spinner.
	 */
	private String[] timeConsumedItems = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	
	/**
	 * Current value of unit selection.
	 */
	private String unitValue = "pieces";
	/**
	 * Current value of time selection.
	 */
	private String timeConsumedValue = "Before 11";

	/**
	 * Called when the activity is starting.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.meals);
		
		mealAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.meal);
		updateAutocomplete();
		amountEditText = (EditText) findViewById(R.id.amount);
		
		unitSpinner = (Spinner) findViewById(R.id.unit);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, unitsItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitSpinner.setAdapter(adapter);
		unitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				unitValue = unitsItems[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				unitValue = unitsItems[3];
			}
		});
		
		timeConsumedSpinner = (Spinner) findViewById(R.id.timeConsumed);
		ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, timeConsumedItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeConsumedSpinner.setAdapter(adapter2);
		timeConsumedSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				timeConsumedValue = timeConsumedItems[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				timeConsumedValue = timeConsumedItems[2];
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

	}

	/**
	 * Save form data to database.
	 * 
	 * Before saving data is checked.
	 * 
	 * @return true if data was successfully saved
	 */
	private boolean save() {
		String meal = mealAutoCompleteTextView.getText().toString();
		if (meal.equals(""))
			return false;
		
		String amount = amountEditText.getText().toString();
		if (amount.equals(""))
			return false;
		
		int amountInt = 0;
	    try {
	      amountInt = Integer.parseInt(amount.trim());
	    }
	    catch (NumberFormatException nfe) {
	      return false;
	    }
	    if (amountInt <= 0)
	    	return false;
	    
        RecordFood food = new RecordFood();
        food.name = meal;
        food.amount = amountInt;
        food.unit = unitValue;
        food.timeConsumed = timeConsumedValue;
		
		try {
			DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
			Record today = dbHandler.getRecord(Calendar.getInstance());
			today.addFood(food);
			dbHandler.addOrUpdateRecord(today);

			dbHandler.addToFoodDictionary(new goodfeeling.db.Food(
					meal));

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		amountEditText.setText("");
		mealAutoCompleteTextView.setText("");
		mealAutoCompleteTextView.requestFocus();
		updateAutocomplete();
		return true;
	}

	/**
	 * Get food dictionary.
	 * 
	 * @return list of meals
	 */
	private String[] getFoodDict() {
		ArrayList<String> activities = new ArrayList<String>();
		try {
			DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
			ArrayList<goodfeeling.db.Food> dbActivities = dbHandler
					.getFoodDictionaryList();
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

	/**
	 * Update autocomplete list of meal names.
	 */
	private void updateAutocomplete() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				getFoodDict());
		mealAutoCompleteTextView.setAdapter(adapter);
	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
