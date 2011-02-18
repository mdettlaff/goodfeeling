package goodfeeling.gui;

import java.util.Iterator;

import goodfeeling.common.AndroidFileIO;
import goodfeeling.common.Table;
import goodfeeling.db.DbHandler;
import goodfeeling.weka.Rule;
import goodfeeling.weka.RulesFinder;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
		
		start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				start.setVisibility(View.GONE);
				SuggestionsNote.setVisibility(View.GONE);
                showDialog(PROGRESS_DIALOG);
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
	
	

    static final int PROGRESS_DIALOG = 0;
    ProgressThread progressThread;
    ProgressDialog progressDialog;
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(Suggestions.this);
            progressDialog.setMessage("Loading. Please wait.");
            progressThread = new ProgressThread(handler);
            progressThread.start();
            return progressDialog;
        default:
            return null;
        }
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int total = msg.getData().getInt("finished");
            if (total==1) {
            	dismissDialog(PROGRESS_DIALOG);
            	String res = progressThread.results;
				if (res=="")
					SuggestionsContent.setText("No suggestions found. Try to add more data.");
				else
					SuggestionsContent.setText(res);
            }
        }
    };

    private class ProgressThread extends Thread {
        Handler mHandler;
        public String results = "";
       
        ProgressThread(Handler h) {
            mHandler = h;
        }
       
        public void run() {
        	results = getSuggestions();
            Message msg = mHandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("finished", 1);
            msg.setData(b);
            mHandler.sendMessage(msg);
                
        }
    }
}