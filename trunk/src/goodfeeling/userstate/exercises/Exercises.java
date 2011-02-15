// Tomasz Dabulis, 2011-02-09, GoodFeeling Project
package goodfeeling.userstate.exercises;

import goodfeeling.gui.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Exercises extends Activity {
   
	public int exerciseSec = 15; // exercise time in sec.
	public TextView timeText; // text countdown
	public TextView exercisesTip; // tip above start button
	public TextView exercisesDescription; // text description of exercise, displaying before start
	
	public ExercisesCounter counter; // special counter for countdown
	public EditText exercisesResultInput; // number input for result
	public Button exercisesSaveButton; // save button for result
	public Button exercisesStartButton; // start button
	public Button exercisesBackButton; // back / cancel button
	public final int show = 0;
	public final int hide = 4;
	
	public String wallPumpsDescription = new String("We stand by the wall / piece of furniture, and lean toward him, arms outstretched on the basis of the pump start. Exercise is leaving in the direction of the walls / furniture and returning to the starting position. The torso remains erect throughout the exercise.");
	public String squatDescription = new String("Arrangement of the arms are straight-forward, parallel to the floor. You can, of course, if someone is accustomed to squat with barbell on shoulders, and the technique is best to go-keep your arms in a neck hold pretending weights.");
	public String wallPumpsTip = new String("Press button below and start doing wall pumps. You will have "+Integer.toString(this.exerciseSec)+" seconds");
	public String squatTip = new String("Press button below and start doing squats. You will have "+Integer.toString(this.exerciseSec)+" seconds");
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercisesmain);
    }
    
	// setup view to start current exercises
	public void initExercisePage(){
		setContentView(R.layout.exercisestraining);
    	exercisesTip = (TextView) findViewById(R.id.ExercisesTip);
    	exercisesTip.setVisibility(show);
    	exercisesDescription = (TextView) findViewById(R.id.ExercisesDescription);
    	exercisesBackButton = (Button) findViewById(R.id.ExercisesBackButton);
    	exercisesDescription.setVisibility(show);
    	exercisesBackButton.setText("Back");
	}
	
	// setup view for squats
    public void goToSquats(View v){
    	this.initExercisePage();
    	exercisesTip.setText(this.squatTip);
    	exercisesDescription.setText(squatDescription);
    }
    
	// setup view for wall pumps
    public void goToWallPumps(View v){
    	this.initExercisePage();
    	exercisesTip.setText(this.wallPumpsTip);
    	exercisesDescription.setText(wallPumpsDescription);
    }
    
    // back to main menu / cancel
    public void goToMainMenu(View v){
    	counter.cancel();
    	setContentView(R.layout.exercisesmain);
    }
    
    // start countdown during doing exercises
    public void startSquats(View v){
    	timeText = (TextView) findViewById(R.id.ExercisesTimeText);
    	exercisesResultInput = (EditText) findViewById(R.id.ExercisesResultInput);
    	exercisesSaveButton = (Button) findViewById(R.id.ExercisesSaveButton);
    	exercisesStartButton = (Button) findViewById(R.id.ExercisesStartButton);
    	exercisesDescription.setVisibility(hide);
    	exercisesStartButton.setClickable(false);
    	exercisesTip.setVisibility(hide);
    	exercisesSaveButton.setVisibility(hide);
    	exercisesResultInput.setVisibility(hide);
    	exercisesBackButton.setText("Cancel");
    	exercisesStartButton.setVisibility(hide);
    	
        counter = new ExercisesCounter((exerciseSec+1)*1000,1000);
    	counter.start();
    }
    
    
    // save to DB, change "MisteryClass.staticMethodWhichSaveResultToDB"
    public void saveAndQuit (View w){
    	//MisteryClass.staticMethodWhichSaveResultToDB( Integer.parseInt(exercisesResultInput.getText().toString()) );
    	ExercisesResult exResult = new ExercisesResult();
    	
    	exResult.setCount(Integer.parseInt(exercisesResultInput.getText().toString()));
    	Intent data = new Intent();
    	exResult.put(data);
    	setResult(RESULT_OK, data);
    	finish();
    	//this.goToMainMenu(w);
    }
    
    
   // special class for counter use to countdown    
    public class ExercisesCounter extends CountDownTimer{

	    public ExercisesCounter(long millisInFuture, long countDownInterval) {
	    	super(millisInFuture, countDownInterval);
	    }
	
	    @Override
	    public void onFinish() {
	    	
	    	//todo:play some sound
	    	
	    	timeText.setText("Finish! Please, type your result:");
	    	timeText.setPadding(0, 0, 0, 0);
	    	exercisesSaveButton.setVisibility(show);
	    	exercisesResultInput.setVisibility(show);
	    	exercisesStartButton.setClickable(true);
	    	exercisesStartButton.setText("Retry?");
	    	exercisesStartButton.setVisibility(show);
	    	
	    }
	
	    @Override
	    public void onTick(long millisUntilFinished) {
	    	timeText.setText("Left: " + millisUntilFinished/1000);
	    }
    }
    
    
}