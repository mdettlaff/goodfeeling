package goodfeeling.gui;

import goodfeeling.userstate.picture_test.PictureTest;
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
	
	private final int TEST_NUM = 1;
	
	private TextView[] text;
	
	private Button buttonRun;
	
	private int test[];
	
	private int testStep;
	
	private Intent intent;
	
    @Override
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
        
        this.test = new int[TEST_NUM];
        this.test[0] = TEST_PICTURE_TEST;
        //this.test[1] = TEST_BALLOON;
        this.testStep = 0;
        
        this.buttonRun = (Button)findViewById(R.id.testrun_buttonrun);
        this.buttonRun.setOnClickListener(this);
    }
    
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    	System.out.println(">>>>>>>>> onActivityResult");
    	switch(requestCode) {
    		case TEST_PICTURE_TEST:
    			switch(resultCode) {
    				case RESULT_OK:
    					this.text[0].setText("Result:");
    					int ap[] = data.getIntArrayExtra("PictureTestResultPositive");
    					int a[] = data.getIntArrayExtra("PictureTestResult");
    					if(ap != null && a != null) {
    						for(int i = 1; i < 11; i++)
    							this.text[i].setText("cat[" + i + "] " + ap[i - 1] + " " + a[i -1]);
    						//TODO: cos zrobic z wynikami aplikacji
    					}
    					else {
    						System.out.println(">>>>>>>>> onActivityResult 'TEST_PICTURE_TEST' WTF??? (What a Terrible Failure)");
    					}
    					break;
    				case RESULT_CANCELED:
    					System.out.println(">>>>>>>>> onActivityResult 'TEST_PICTURE_TEST' RESULT_CANCELED. WTF??? (What a Terrible Failure)");
    					break;
    				default:
    					System.out.println(">>>>>>>>> onActivityResult 'TEST_PICTURE_TEST' unknown result");
    			}
    			break;
    		default:
    			System.out.println(">>>>>>>>> onActivityResult unknown activity");
    	}
    	runNextTest();
    }

	@Override
	public void onClick(View v) {
		System.out.println(">>>>>>>>> onClick");
		runNextTest();
	}
	
	// private
	
	private void runNextTest() {
		if(this.testStep >= TEST_NUM) {
			System.out.println(">>>>>>> test end");
			return;
		}
		switch(this.test[this.testStep]) {
			case TEST_PICTURE_TEST:
				intent = (new Intent()).setClass(this, PictureTest.class);
				break;
		}
		startActivityForResult(intent, this.test[this.testStep]);
		this.testStep++;
	}
}