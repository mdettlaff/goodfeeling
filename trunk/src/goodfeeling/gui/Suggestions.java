package goodfeeling.gui;

import java.util.Iterator;

import goodfeeling.common.AndroidFileIO;
import goodfeeling.common.Table;
import goodfeeling.db.DbHandler;
import goodfeeling.weka.Rule;
import goodfeeling.weka.RulesFinder;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Suggestions extends Activity {
	TextView SuggestionsContent, SuggestionsNote, SuggestionsWait;
	Button start;
	public static String result = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestions);

		SuggestionsNote = (TextView)findViewById(R.id.SuggestionsNote);
		SuggestionsContent = (TextView)findViewById(R.id.SuggestionsContent);
		SuggestionsWait = (TextView)findViewById(R.id.SuggestionsWait);

		
		
		
		start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				start.setVisibility(View.INVISIBLE);
				SuggestionsNote.setVisibility(View.INVISIBLE);
				SuggestionsWait.setVisibility(View.VISIBLE);
				
				// myślałem, że to zlikwiduje zwiechę podczas 
				// obliczeń, ale się myliłem...
				Thread t = new Thread() {
				    public void run() {
				    	Suggestions.result = getSuggestions();
				    }
				};
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SuggestionsWait.setVisibility(View.INVISIBLE);
				SuggestionsContent.setText(Suggestions.result);
				
			}
		});
	}
	
	private String getSuggestions() {
		DbHandler dbHandler = new DbHandler(new AndroidFileIO(this));
		Table data;
		try {
			data = dbHandler.generateDataTable("mentalrate");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		RulesFinder finder = new RulesFinder(data);
		Iterator<Rule> rulesIter = finder.findRules().iterator();

		String msg = "";
		while (rulesIter.hasNext()) {
		    msg += RuleTranslator.humanReadable(rulesIter.next())+"\n";
		}
	    System.out.println(msg);
		return msg;
	}
}