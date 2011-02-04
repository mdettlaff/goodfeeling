package goodfeeling.gui;

import java.util.ArrayList;
import java.util.List;

import goodfeeling.common.AndroidFileIO;
import goodfeeling.db.DbHandler;
import goodfeeling.userstate.UserStatePersistence;
import goodfeeling.userstate.balloon.Balloon;
import goodfeeling.userstate.balloon.BalloonResult;
import goodfeeling.userstate.balloon.BalloonResultException;
import goodfeeling.userstate.picture_test.PictureTest;
import goodfeeling.userstate.picture_test.PictureTestResult;
import goodfeeling.userstate.picture_test.PictureTestResultException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestRun extends Activity implements OnClickListener {
    
	private final int TEST_PICTURE_TEST = 0;
	private final int TEST_BALLOON = 1;
	
	private final int TEST_NUM = 4;
	
	private TextView[] text;
	private TextView summaryText;
	
	private Button buttonRun;
	
	private int test[];
	
	private int testStep;

	private List<PictureTestResult> pictureTestResults;
	private List<BalloonResult> balloonTestResults;
	private boolean isWholeTestFinished;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testrun);
        
        this.text = new TextView[11];
        this.text[0] = (TextView)findViewById(R.id.testrun_text0);
        this.text[1] = (TextView)findViewById(R.id.testrun_text1);
        this.text[2] = (TextView)findViewById(R.id.testrun_text2);
        this.text[3] = (TextView)findViewById(R.id.testrun_text3);
        this.text[4] = (TextView)findViewById(R.id.testrun_text4);
        this.text[5] = (TextView)findViewById(R.id.testrun_text5);
        this.text[6] = (TextView)findViewById(R.id.testrun_text6);
        this.text[7] = (TextView)findViewById(R.id.testrun_text7);
        this.text[8] = (TextView)findViewById(R.id.testrun_text8);
        this.text[9] = (TextView)findViewById(R.id.testrun_text9);
        this.text[10] = (TextView)findViewById(R.id.testrun_text10);
        summaryText = (TextView)findViewById(R.id.testrun_summary);

        test = new int[TEST_NUM];
        for (int i = 0; i < test.length; i++) {
        	boolean isEven = i % 2 == 0;
        	test[i] = isEven ? TEST_PICTURE_TEST : TEST_BALLOON;
        }
        this.testStep = 0;
        
        this.buttonRun = (Button)findViewById(R.id.testrun_buttonrun);
        this.buttonRun.setOnClickListener(this);

    	pictureTestResults = new ArrayList<PictureTestResult>();
    	balloonTestResults = new ArrayList<BalloonResult>();
    	isWholeTestFinished = false;
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    		case TEST_PICTURE_TEST:
    			switch(resultCode) {
    				case RESULT_OK:
    					try {
    						PictureTestResult result = new PictureTestResult(data);
    						for(int i = 0; i < result.getCategoryCount(); i++)
    							this.text[i + 1].setText(String.format("PictureTest result: cat %d, positive %d, all %d",
    								i,
    								result.getAnswerPositive(i),
    								result.getAnswer(i)));		
    						pictureTestResults.add(result);
    					} catch (PictureTestResultException e) {
    						this.text[0].setText("PictureTest result: null");
    					}
    					break;
    				case RESULT_CANCELED:
    					this.text[0].setText("PictureTest result: RESULT_CANCELED");
    					break;
    				default:
    					System.out.println(">>> 'TEST_PICTURE_TEST' unknown resultCode");
    			}
    			break;
    		case TEST_BALLOON:
    			switch(resultCode) {
    				case RESULT_OK:
    					try {
    						BalloonResult result = new BalloonResult(data);
    						this.text[0].setText(String.format("Balloon result: correct %d, incorrect %d, all %d.",
    							result.getCorrect(),
    							result.getIncorrect(),
    							result.getAll()));
    						balloonTestResults.add(result);
    					} catch(BalloonResultException e) {
    						this.text[0].setText("Balloon result: null");
    					}
    					break;
    				case RESULT_CANCELED:
    					this.text[0].setText("Balloon result: RESULT_CANCELED");
    					break;
    				default:
    					System.out.println(">>> 'TEST_BALLOON' unknown resultCode");
    			}
    			break;
    		default:
    			System.out.println(">>> onActivityResult unknown activity");
    	}
    	runNextTest();
    }

    // OnClickListener implements
    
	public void onClick(View v) {
		runNextTest();
	}

	// private

	private void runNextTest() {
		if (this.testStep >= test.length) {
			if (!isWholeTestFinished) {
				onWholeTestFinished();
				isWholeTestFinished = true;
			}
			return;
		}
		Intent intent;
		switch(this.test[this.testStep]) {
			case TEST_PICTURE_TEST:
				intent = new Intent(this, PictureTest.class);
				break;
			case TEST_BALLOON:
				intent = new Intent(this, Balloon.class);
				break;
			default:
				return;
		}
		startActivityForResult(intent, this.test[this.testStep]);
		this.testStep++;
	}

	private void onWholeTestFinished() {
		UserStatePersistence persistence = new UserStatePersistence(
				new DbHandler(new AndroidFileIO(this)));
		persistence.persistResults(pictureTestResults, balloonTestResults);
		summaryText.setText(String.format("Summary: mood rate = %s, mental rate = %s",
				persistence.getCurrentMood(),
				persistence.getCurrentMentalPerformance()));
	}
}
